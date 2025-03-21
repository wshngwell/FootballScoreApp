package com.example.footballscoreapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballscoreapp.domain.entities.LeagueEntity
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.MatchEntity
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.usecases.GetMatchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class MatchesViewModel(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val leagueEntity: LeagueEntity,
    private val date: Date
) : ViewModel() {


    data class State(
        val leagueEntity: LeagueEntity = LeagueEntity("", "", ""),
        val isLoading: Boolean = false,
        val leaguesList: List<MatchEntity> = listOf()
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    sealed interface Intent {
        data class OnMatchClicked(val matchEntity: MatchEntity) : Intent
    }

    sealed interface Event {
        data class Error(val ex: LoadingException) : Event
        data class OnMatchClicked(val matchEntity: MatchEntity) : Event
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow


    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.OnMatchClicked -> _event.emit(Event.OnMatchClicked(intent.matchEntity))
        }
    }

    init {
        _state.update { it.copy(leagueEntity = leagueEntity) }
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
                            leaguesList = tResult.data.filter { match -> match.leagueInfo == leagueEntity }
                        )
                    }
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}