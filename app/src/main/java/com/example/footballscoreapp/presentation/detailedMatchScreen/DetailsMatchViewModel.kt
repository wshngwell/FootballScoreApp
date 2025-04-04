package com.example.footballscoreapp.presentation.detailedMatchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballscoreapp.domain.SingleFlowEvent
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.entities.detailMatchInfo.MatchDetailInfoEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.lineup.FootballPlayerEntity
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.domain.usecases.detailMatchInfoUseCases.GetDetailedMatchInfoUseCase
import com.example.footballscoreapp.presentation.mockDetailInfoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsMatchViewModel(
    private val getDetailedMatchInfoUseCase: GetDetailedMatchInfoUseCase,
    private val matchEntity: MatchEntity
) : ViewModel() {

    data class State(
        val matchEntity: MatchEntity,
        val detailInfoEntity: MatchDetailInfoEntity = mockDetailInfoEntity,
        val isLoading: Boolean = false,
        val error: LoadingException? = null
    )

    private val _state = MutableStateFlow(State(matchEntity = matchEntity))
    val state = _state.asStateFlow()

    sealed interface Intent {
        data class OnPlayerClicked(val footballPlayerEntity: FootballPlayerEntity) : Intent
    }

    sealed interface Event {
        data class NavigateToPlayerDetailsScreen(val footballPlayerEntity: FootballPlayerEntity) :
            Event
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    init {
        viewModelScope.launch {
            val detailInfoTresult = getDetailedMatchInfoUseCase(state.value.matchEntity.matchId)
            when (detailInfoTresult) {
                is TResult.Error -> {
                    _state.update { it.copy(error = detailInfoTresult.exception) }
                }

                is TResult.Success -> {
                    _state.update { it.copy(detailInfoEntity = detailInfoTresult.data) }
                }
            }
        }
    }

    fun sendIntent(intent: Intent) {

    }
}