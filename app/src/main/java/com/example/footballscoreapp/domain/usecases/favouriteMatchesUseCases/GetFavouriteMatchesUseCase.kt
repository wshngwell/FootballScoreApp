package com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases

import com.example.footballscoreapp.domain.repositories.IFavouriteMatchesRepository

class GetFavouriteMatchesUseCase(
    private val iFavouriteMatchesRepository: IFavouriteMatchesRepository
) {
    operator fun invoke() = iFavouriteMatchesRepository.favouriteMatches
}