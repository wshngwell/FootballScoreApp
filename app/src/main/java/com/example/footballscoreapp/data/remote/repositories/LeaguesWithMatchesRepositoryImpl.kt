package com.example.footballscoreapp.data.remote.repositories

import com.example.footballscoreapp.data.remote.ApiService
import com.example.footballscoreapp.data.remote.mappers.mapToMatchEntity
import com.example.footballscoreapp.data.remote.parseToLoadingException
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.MatchEntity
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.repositories.ILeaguesWithMatchesRepository
import com.example.footballscoreapp.utils.myLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LeaguesWithMatchesRepositoryImpl(
    private val apiService: ApiService
) : ILeaguesWithMatchesRepository {

    override suspend fun loadMatchesForLeague(date: Date): TResult<List<MatchEntity>, LoadingException> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {

                val formatter = SimpleDateFormat("'eq.'yyyy-MM-dd", Locale.getDefault())
                val formattedDate = formatter.format(date)

                val matchesEntities =
                    apiService.getMatchesByDate(formattedDate).flatMap { matches ->
                        myLog("С сервера пришло " + matches.matches.size.toString())
                        matches.matches
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