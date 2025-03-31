package com.example.footballscoreapp.presentation.liveMatchesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballscoreapp.domain.SingleFlowEvent
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.MatchEntity
import com.example.footballscoreapp.domain.entities.MatchStatusEntity
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.usecases.GetMatchesUseCase
import com.example.footballscoreapp.presentation.leagueScreen.LeaguesWithMatchesUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class LiveMatchesViewModel(
    private val getMatchesUseCase: GetMatchesUseCase,
) : ViewModel() {

    data class State(
        val isLoading: Boolean = false,
        val matchCount: Int = 0,
        val leaguesWithMatchesUIModelList: List<LeaguesWithMatchesUIModel> = listOf(),
        val error: LoadingException? = null
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    sealed interface Intent {
        data object LoadMatches : Intent
        data class OnMatchClicked(val matchEntity: MatchEntity) : Intent
    }

    sealed interface Event {
        data class OnNavigateToDetailedMatchesScreen(val matchEntity: MatchEntity) : Event
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    fun sendIntent(intent: Intent) {
        when (intent) {
            Intent.LoadMatches -> {
                loadLiveMatchesInScope()
            }

            is Intent.OnMatchClicked -> {
                _event.emit(Event.OnNavigateToDetailedMatchesScreen(intent.matchEntity))
            }
        }
    }

    private fun loadLiveMatchesInScope() {
        viewModelScope.launch {
            loadLiveMatches()
        }
    }

    init {
        loadLiveMatchesInScope()
    }

    private suspend fun loadLiveMatches() {
        val date = Calendar.getInstance().time
        _state.update { it.copy(isLoading = true) }
        val tResult = getMatchesUseCase(date)
        when (tResult) {
            is TResult.Error -> {
                _state.update { it.copy(error = tResult.exception) }
            }

            is TResult.Success -> {
                val matchCount =
                    tResult.data.filter { it.status == MatchStatusEntity.STARTED }.count()
                val map = tResult.data.filter { it.status == MatchStatusEntity.STARTED }
                    .groupBy {
                        it.leagueInfo
                    }
                val distinctLeaguesWithMatches = map.keys.distinctBy { it.leagueId }
                val leagueListWithMatches = distinctLeaguesWithMatches.map {
                    LeaguesWithMatchesUIModel(
                        league = it,
                        matches = map[it]!!,
                    )
                }
                _state.update {
                    it.copy(
                        matchCount = matchCount,
                        leaguesWithMatchesUIModelList = leagueListWithMatches
                    )
                }
            }
        }
        _state.update { it.copy(isLoading = false) }
    }
}