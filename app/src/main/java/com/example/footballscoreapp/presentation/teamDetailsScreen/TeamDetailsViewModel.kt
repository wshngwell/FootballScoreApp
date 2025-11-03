package com.example.footballscoreapp.presentation.teamDetailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballscoreapp.domain.SingleFlowEvent
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.entities.teams.TeamFullInfoEntity
import com.example.footballscoreapp.domain.entities.teams.TeamMainInfoEntity
import com.example.footballscoreapp.domain.usecases.teamsFullInfoUseCases.GetTeamFullInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeamDetailsViewModel(
    teamMainInfoEntity: TeamMainInfoEntity,
    private val getTeamFullInfoUseCase: GetTeamFullInfoUseCase
) : ViewModel() {

    data class State(
        val teamMainInfoEntity: TeamMainInfoEntity,
        val teamFullInfoEntity: TeamFullInfoEntity? = null,
        val isLoading: Boolean = false,
        val error: LoadingException? = null
    )

    private val _state = MutableStateFlow(
        State(teamMainInfoEntity = teamMainInfoEntity)
    )
    val state = _state.asStateFlow()

    sealed interface Intent {

    }

    sealed interface Event {

    }

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = getTeamFullInfoUseCase(_state.value.teamMainInfoEntity.id)
            when (result) {
                is TResult.Error -> {
                    _state.update { it.copy(error = result.exception) }
                }

                is TResult.Success -> {
                    _state.update { it.copy(teamFullInfoEntity = result.data) }
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    fun sendIntent(intent: Intent) {
    }

}