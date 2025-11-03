package com.example.footballscoreapp.domain.usecases.detailMatchInfoUseCases

import com.example.footballscoreapp.domain.repositories.IDetailsMatchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDetailedMatchInfoUseCase(
    private val iDetailsMatchRepository: IDetailsMatchRepository
) {
    suspend operator fun invoke(matchId: String) = withContext(Dispatchers.IO) {
        iDetailsMatchRepository.loadMatchDetailedInfo(matchId)
    }
}