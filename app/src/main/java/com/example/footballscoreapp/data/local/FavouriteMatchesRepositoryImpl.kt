package com.example.footballscoreapp.data.local

import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.domain.repositories.IFavouriteMatchesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext

class FavouriteMatchesRepositoryImpl(
    private val matchesDao: MatchesDao
) : IFavouriteMatchesRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    override val favouriteMatches: SharedFlow<List<MatchEntity>> =
        matchesDao.getMatches().map { listOfMatches ->
            listOfMatches.map {
                it.toMatchEntity()
            }
        }.buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
            .distinctUntilChanged()
            .shareIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(
                    stopTimeoutMillis = 10_000,
                    replayExpirationMillis = 0
                ),
                replay = 1
            )

    override suspend fun addMatchToFavourite(listOfMatchEntities: List<MatchEntity>) =
        withContext(Dispatchers.IO) {
            val listOfMatchDbModels = listOfMatchEntities.map {
                it.toMatchDbModel()
            }
            matchesDao.addMatchToDb(listOfMatchDbModels)
        }


    override suspend fun deleteMatchesFromFavourite(listOfMatchId: List<String>) =
        withContext(Dispatchers.IO) {
            matchesDao.deleteMatchFromDb(listOfMatchId)
        }

}