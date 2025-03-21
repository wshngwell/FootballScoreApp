package com.example.footballscoreapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballscoreapp.domain.entities.LeagueEntity
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.usecases.GetMatchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class LeaguesViewModel(
    private val getMatchesUseCase: GetMatchesUseCase,
    val date: Date
) : ViewModel() {


    data class State(
        val isLoading: Boolean = false,
        val leaguesList: List<LeagueEntity> = listOf()
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    sealed interface Intent {
        data class OnLeagueClicked(val leagueEntity: LeagueEntity) : Intent
    }

    sealed interface Event {
        data class Error(val ex: LoadingException) : Event
        data class OnLeagueClicked(val leagueEntity: LeagueEntity) : Event
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow


    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.OnLeagueClicked -> _event.emit(Event.OnLeagueClicked(intent.leagueEntity))
        }
    }

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val tResult = getMatchesUseCase(date)
            when (tResult) {
                is TResult.Error -> {
                    _event.emit(Event.Error(tResult.exception))
                }

                is TResult.Success -> {
                    _state.update {
                        it.copy(
                            leaguesList = tResult.data.map { matches -> matches.leagueInfo }
                        )
                    }
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}