package com.example.footballscoreapp.domain.usecases

import com.example.footballscoreapp.domain.repositories.ILeaguesWithMatchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMatchesUseCase(
    private val iLeaguesWithMatchesRepository: ILeaguesWithMatchesRepository
) {
    suspend operator fun invoke(date: String) = withContext(Dispatchers.IO) {
        iLeaguesWithMatchesRepository.loadMatchesForLeague(date)
    }
}