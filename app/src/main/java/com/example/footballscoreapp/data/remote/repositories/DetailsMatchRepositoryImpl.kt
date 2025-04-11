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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DetailsMatchRepositoryImpl(
    private val apiService: ApiService
) : IDetailsMatchRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun loadMatchDetailedInfo(matchId: String): TResult<MatchDetailInfoEntity, LoadingException> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {
                val formattedMatchId = "eq.${matchId}"

                val mapOfAdditionalInfoAndLineUp = scope.async {
                    val additionalMatchInfoFromNet =
                        apiService.getMatchAdditionalInfo(formattedMatchId)
                    val additionalMatchInfo = additionalMatchInfoFromNet.firstNotNullOfOrNull {
                        it.toMatchAdditionalInfoEntity()
                    }

                    val lineUps = additionalMatchInfo?.lineupsId?.let {
                        apiService.getLineUp("eq.${it}").first()
                            .toLineUpEntity()
                    }
                    mapOf(additionalMatchInfo to lineUps)
                }

                val matchStatistics = scope.async {
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
                        matchAdditionalInfoEntity = additionalInfoWithLineUpResult.keys.firstOrNull(),
                        teamStatisticsEntity = matchStatisticsResult,
                        lineUpEntity = additionalInfoWithLineUpResult[additionalInfoWithLineUpResult.keys.firstOrNull()],
                    )
                )

            }.getOrElse {
                TResult.Error<MatchDetailInfoEntity, LoadingException>(exception = it.parseToLoadingException())
            }
        }
}