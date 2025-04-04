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
import kotlinx.coroutines.withContext

class DetailsMatchRepositoryImpl(
    private val apiService: ApiService
) : IDetailsMatchRepository {

    override suspend fun loadMatchDetailedInfo(matchId: String): TResult<MatchDetailInfoEntity, LoadingException> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {
                val formattedMatchId = "eq.${matchId}"

                val additionalMatchInfoFromNet = apiService.getMatchAdditionalInfo(formattedMatchId)
                val additionalMatchInfo = if (additionalMatchInfoFromNet.firstOrNull() == null) {
                    null
                } else {
                    additionalMatchInfoFromNet.first().toMatchAdditionalInfoEntity()
                }

                val lineUps = additionalMatchInfo?.lineupsId?.let {
                    apiService.getLineUp("eq.${it}").first()
                        .toLineUpEntity()
                }

                val matchStatisticsFromNet = apiService.getMatchStatistics(formattedMatchId)
                val matchStatistics = if (matchStatisticsFromNet.firstOrNull() == null) {
                    null
                } else {
                    matchStatisticsFromNet.first()
                        .toListOfTeamStatisticsEntity()
                }

                TResult.Success<MatchDetailInfoEntity, LoadingException>(
                    data = MatchDetailInfoEntity(
                        matchAdditionalInfoEntity = additionalMatchInfo,
                        teamStatisticsEntity = matchStatistics,
                        lineUpEntity = lineUps
                    )
                )

            }.getOrElse {
                TResult.Error<MatchDetailInfoEntity, LoadingException>(exception = it.parseToLoadingException())
            }
        }
}