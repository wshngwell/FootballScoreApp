package com.example.footballscoreapp.presentation.favouriteMatchesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballscoreapp.domain.SingleFlowEvent
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases.AddMatchToFavouriteUseCase
import com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases.DeleteMatchFromFavouriteUseCase
import com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases.GetFavouriteMatchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouriteMatchesViewModel(
    private val addMatchToFavouriteUseCase: AddMatchToFavouriteUseCase,
    private val deleteMatchFromFavouriteUseCase: DeleteMatchFromFavouriteUseCase,
    private val getFavouriteMatchesUseCase: GetFavouriteMatchesUseCase
) : ViewModel() {

    data class State(
        val favouriteMatchesList: List<MatchEntity> = listOf(),
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val result = getFavouriteMatchesUseCase().firstOrNull().orEmpty()
            _state.update { it.copy(favouriteMatchesList = result) }

            getFavouriteMatchesUseCase().collect { db ->
                _state.update {
                    it.copy(
                        favouriteMatchesList = (db + it.favouriteMatchesList).distinctBy { it.matchId }
                    )
                }
            }
        }
    }


    sealed interface Intent {
        data class OnMatchClicked(val matchEntity: MatchEntity) : Intent
        data class OnAddOrDeleteMatchFromFavouriteClicked(val matchEntity: MatchEntity) : Intent
    }

    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.OnAddOrDeleteMatchFromFavouriteClicked -> {
                viewModelScope.launch {
                    if (intent.matchEntity.isFavourite) {
                        deleteMatchFromFavouriteUseCase(listOf(intent.matchEntity.matchId))
                    } else {
                        addMatchToFavouriteUseCase(listOf(intent.matchEntity))
                    }
                    _state.update {
                        it.copy(favouriteMatchesList = it.favouriteMatchesList.map {
                            if (it.matchId == intent.matchEntity.matchId) it.copy(isFavourite = it.isFavourite.not())
                            else it
                        })
                    }
                }

            }

            is Intent.OnMatchClicked -> {
                _event.emit(Event.OnNavigateToDetailedMatchesScreen(intent.matchEntity))
            }
        }
    }

    sealed interface Event {
        data class OnNavigateToDetailedMatchesScreen(val matchEntity: MatchEntity) : Event
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

}