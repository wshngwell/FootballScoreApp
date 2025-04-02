package com.example.footballscoreapp.domain.repositories

import com.example.footballscoreapp.domain.entities.MatchEntity
import kotlinx.coroutines.flow.SharedFlow

interface IFavouriteMatchesRepository {

    val favouriteMatches: SharedFlow<List<MatchEntity>>

    suspend fun addMatchToFavourite(listOfMatchEntities: List<MatchEntity>)

    suspend fun deleteMatchesFromFavourite(listOfMatchId: List<String>)
}