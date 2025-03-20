package com.example.footballscoreapp.data.remote.repositories

import com.example.footballscoreapp.data.remote.ApiService
import com.example.footballscoreapp.data.remote.mappers.mapToLeagueEntity
import com.example.footballscoreapp.data.remote.mappers.mapToMatchEntity
import com.example.footballscoreapp.data.remote.parseToLoadingException
import com.example.footballscoreapp.domain.entities.LeagueEntity
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.MatchEntity
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.repositories.ILeaguesWithMatchesRepository
import com.example.footballscoreapp.utils.myLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LeaguesWithMatchesRepositoryImpl(
    private val apiService: ApiService
) : ILeaguesWithMatchesRepository {

    override suspend fun loadLeagues(date: String): TResult<List<LeagueEntity>, LoadingException> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {
                val leaguesEntities =
                    apiService.getMatchesByDate(date = date).flatMap { matchesInfo ->
                        matchesInfo.matches.take(50)
                            .mapNotNull {
                                it.mapToLeagueEntity()
                            }
                    }

                TResult.Success<List<LeagueEntity>, LoadingException>(data = leaguesEntities)
            }.getOrElse {
                TResult.Error(it.parseToLoadingException())
            }
        }

    override suspend fun loadMatchesForLeague(date: String): TResult<List<MatchEntity>, LoadingException> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {
                val matchesEntities = apiService.getMatchesByDate(date = date).flatMap { matches ->
                    myLog("С сервера пришло " + matches.matches.size.toString())
                    matches.matches.take(50)
                        .mapNotNull {
                            myLog(it.startTime.toString())
                            it.mapToMatchEntity()
                        }
                }
                TResult.Success<List<MatchEntity>, LoadingException>(data = matchesEntities)
            }.getOrElse {
                TResult.Error(it.parseToLoadingException())
            }
        }

}