package com.example.footballscoreapp.data.remote.repositories

import com.example.footballscoreapp.data.remote.ApiService
import com.example.footballscoreapp.data.remote.mappers.toLineUpEntity
import com.example.footballscoreapp.data.remote.mappers.toListOfTeamStatisticsEntity
import com.example.footballscoreapp.data.remote.mappers.toMatchAdditionalInfoEntity
import com.example.footballscoreapp.data.remote.parseToLoadingException
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.entities.detailMatchInfo.MatchDetailInfoEntity
import com.example.footballscoreapp.domain.repositories.IDetailsMatchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DetailsMatchRepositoryImpl(
    private val apiService: ApiService
) : IDetailsMatchRepository {


    override suspend fun loadMatchDetailedInfo(matchId: String): TResult<MatchDetailInfoEntity, LoadingException> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {
                val formattedMatchId = "eq.${matchId}"

                val mapOfAdditionalInfoAndLineUp = async {
                    val additionalMatchInfoFromNet =
                        apiService.getMatchAdditionalInfo(formattedMatchId)
                    val additionalMatchInfo = additionalMatchInfoFromNet.firstNotNullOfOrNull {
                        it.toMatchAdditionalInfoEntity()
                    }

                    val lineUps = additionalMatchInfo?.lineupsId?.let {
                        apiService.getLineUp("eq.${it}").first()
                            .toLineUpEntity()
                    }
                    additionalMatchInfo to lineUps
                }

                val matchStatistics = async {
                    val matchStatisticsFromNet = apiService.getMatchStatistics(formattedMatchId)
                    val matchStatistics = matchStatisticsFromNet.firstNotNullOfOrNull {
                        it.toListOfTeamStatisticsEntity()
                    }
                    matchStatistics
                }

                val additionalInfoWithLineUpResult = mapOfAdditionalInfoAndLineUp.await()
                val matchStatisticsResult = matchStatistics.await()

                TResult.Success<MatchDetailInfoEntity, LoadingException>(
                    data = MatchDetailInfoEntity(
                        matchAdditionalInfoEntity = additionalInfoWithLineUpResult.first,
                        teamStatisticsEntity = matchStatisticsResult,
                        lineUpEntity = additionalInfoWithLineUpResult.second,
                    )
                )

            }.getOrElse {
                TResult.Error<MatchDetailInfoEntity, LoadingException>(exception = it.parseToLoadingException())
            }
        }
}