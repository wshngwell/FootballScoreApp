package com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases

import com.example.footballscoreapp.domain.entities.MatchEntity
import com.example.footballscoreapp.domain.repositories.IFavouriteMatchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddMatchToFavouriteUseCase(
    private val iFavouriteMatchesRepository: IFavouriteMatchesRepository
) {
    suspend operator fun invoke(listOfMatchEntities: List<MatchEntity>) =
        withContext(Dispatchers.IO) {
            iFavouriteMatchesRepository.addMatchToFavourite(listOfMatchEntities)
        }
}