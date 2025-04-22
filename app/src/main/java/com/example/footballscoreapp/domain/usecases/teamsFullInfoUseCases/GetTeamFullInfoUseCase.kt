package com.example.footballscoreapp.domain.usecases.teamsFullInfoUseCases

import com.example.footballscoreapp.domain.repositories.ITeamsFullInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTeamFullInfoUseCase(
    private val iTeamsFullInfoRepository: ITeamsFullInfoRepository
) {
    suspend operator fun invoke(teamId: String) = withContext(Dispatchers.IO) {
        iTeamsFullInfoRepository.getTeamFullInfo(teamId)
    }
}