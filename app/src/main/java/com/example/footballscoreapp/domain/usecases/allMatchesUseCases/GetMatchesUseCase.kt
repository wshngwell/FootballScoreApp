package com.example.footballscoreapp.domain.usecases.allMatchesUseCases

import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.repositories.IFavouriteMatchesRepository
import com.example.footballscoreapp.domain.repositories.ILeaguesWithMatchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.util.Date

class GetMatchesUseCase(
    private val iLeaguesWithMatchesRepository: ILeaguesWithMatchesRepository,
    private val iFavouriteMatchesRepository: IFavouriteMatchesRepository
) {
    suspend operator fun invoke(date: Date) = withContext(Dispatchers.IO) {
        val matchesFromNetwork = iLeaguesWithMatchesRepository.loadMatchesForLeague(date)
        return@withContext when (matchesFromNetwork) {
            is TResult.Error -> matchesFromNetwork
            is TResult.Success -> {
                val favouriteListIds =
                    iFavouriteMatchesRepository.favouriteMatches.firstOrNull().orEmpty()
                        .map { it.matchId }
                val newFavouriteMatches =
                    matchesFromNetwork.data.filter { favouriteListIds.contains(it.matchId) }

                iFavouriteMatchesRepository.addMatchToFavourite(newFavouriteMatches)

                return@withContext TResult.Success(data = matchesFromNetwork.data.map {
                    if (favouriteListIds.contains(it.matchId)) {
                        it.copy(isFavourite = true)
                    } else it
                })

            }
        }
    }
}
