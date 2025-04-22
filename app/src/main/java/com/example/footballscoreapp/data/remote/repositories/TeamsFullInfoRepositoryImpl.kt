package com.example.footballscoreapp.data.remote.repositories

import com.example.footballscoreapp.data.remote.ApiService
import com.example.footballscoreapp.data.remote.mappers.toTeamFullInfoEntity
import com.example.footballscoreapp.data.remote.parseToLoadingException
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.entities.teams.TeamFullInfoEntity
import com.example.footballscoreapp.domain.repositories.ITeamsFullInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TeamsFullInfoRepositoryImpl(
    private val apiService: ApiService
) : ITeamsFullInfoRepository {

    override suspend fun getTeamFullInfo(teamId: String): TResult<TeamFullInfoEntity, LoadingException> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {

                val formattedTeamId = "eq.${teamId}"

                val teamFullInfoEntity =
                    apiService.getTeamInfo(formattedTeamId).firstNotNullOfOrNull {
                        it.toTeamFullInfoEntity()
                    }
                if (teamFullInfoEntity == null) {
                    TResult.Error<TeamFullInfoEntity, LoadingException>(
                        LoadingException.OtherError(
                            Throwable()
                        )
                    )
                } else {
                    TResult.Success<TeamFullInfoEntity, LoadingException>(data = teamFullInfoEntity)
                }
            }.getOrElse {
                TResult.Error<TeamFullInfoEntity, LoadingException>(it.parseToLoadingException())
            }
        }
}