package com.example.footballscoreapp.domain.usecases

import com.example.footballscoreapp.domain.repositories.ILeaguesWithMatchesRepository

class GetLeaguesUseCase(
    private val iLeaguesWithMatchesRepository: ILeaguesWithMatchesRepository
) {
    suspend operator fun invoke() = iLeaguesWithMatchesRepository.loadLeagues()
}