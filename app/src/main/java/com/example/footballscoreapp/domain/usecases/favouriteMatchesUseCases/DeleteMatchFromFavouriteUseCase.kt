package com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases

import com.example.footballscoreapp.domain.repositories.IFavouriteMatchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteMatchFromFavouriteUseCase(
    private val iFavouriteMatchesRepository: IFavouriteMatchesRepository
) {
    suspend operator fun invoke(matchId: Int) = withContext(Dispatchers.IO) {
        iFavouriteMatchesRepository.deleteMatchesFromFavourite(matchId)
    }
}