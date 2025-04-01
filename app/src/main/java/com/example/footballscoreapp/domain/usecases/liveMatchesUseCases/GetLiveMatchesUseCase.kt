package com.example.footballscoreapp.domain.usecases.liveMatchesUseCases

import com.example.footballscoreapp.domain.entities.MatchStatusEntity
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.usecases.allMatchesUseCases.GetMatchesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class GetLiveMatchesUseCase(
    private val getMatchesUseCase: GetMatchesUseCase
) {
    suspend operator fun invoke(date: Date) = withContext(Dispatchers.IO) {
        return@withContext when (val tResult = getMatchesUseCase(date)) {
            is TResult.Error -> tResult
            is TResult.Success -> TResult.Success(tResult.data.filter { it.status == MatchStatusEntity.STARTED })
        }
    }
}