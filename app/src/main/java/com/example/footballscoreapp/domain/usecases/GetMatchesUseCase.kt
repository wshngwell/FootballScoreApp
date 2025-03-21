package com.example.footballscoreapp.domain.usecases

import com.example.footballscoreapp.domain.repositories.ILeaguesWithMatchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class GetMatchesUseCase(
    private val iLeaguesWithMatchesRepository: ILeaguesWithMatchesRepository
) {
    suspend operator fun invoke(date: Date) = withContext(Dispatchers.IO) {
        iLeaguesWithMatchesRepository.loadMatchesForLeague(date)
    }
}