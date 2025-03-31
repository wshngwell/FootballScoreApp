package com.example.footballscoreapp.presentation.leagueScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballscoreapp.domain.SingleFlowEvent
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.MatchEntity
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.usecases.GetMatchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class LeaguesViewModel(
    private val getMatchesUseCase: GetMatchesUseCase,
) : ViewModel() {


    data class State(
        val today: DayState = DayState(),
        val tomorrow: DayState = DayState(),
        val yesterday: DayState = DayState(),
        val leagueDayLists: List<LeagueDay> = listOf(
            LeagueDay.YESTERDAY,
            LeagueDay.TODAY,
            LeagueDay.TOMORROW
        ),
        val currentLeagueDay: LeagueDay = LeagueDay.TODAY,
    )

    data class DayState(
        val isLoading: Boolean = false,
        val matchCount: Int = 0,
        val leaguesWithMatchesUIModelList: List<LeaguesWithMatchesUIModel> = listOf(),
        val error: LoadingException? = null
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    sealed interface Intent {
        data class ChangeCurrentDay(val leagueDay: LeagueDay) : Intent
        data object LoadLeagues : Intent
        data class OnMatchClicked(val matchEntity: MatchEntity) : Intent
    }

    sealed interface Event {
        data class OnNavigateToDetailedMatchesScreen(val matchEntity: MatchEntity) : Event
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.ChangeCurrentDay -> {
                _state.update { it.copy(currentLeagueDay = intent.leagueDay) }
            }

            is Intent.LoadLeagues -> {
                launchLoadLeagues()
            }

            is Intent.OnMatchClicked -> _event.emit(Event.OnNavigateToDetailedMatchesScreen(intent.matchEntity))
        }
    }

    init {
        launchLoadLeagues()
    }

    private fun launchLoadLeagues() {
        state.value.leagueDayLists.forEach {
            viewModelScope.launch {
                loadLeagues(it)
            }
        }
    }

    private suspend fun loadLeagues(leagueDay: LeagueDay) {
        _state.update {
            when (leagueDay) {
                LeagueDay.TODAY -> it.copy(today = it.today.copy(isLoading = true))
                LeagueDay.TOMORROW -> it.copy(tomorrow = it.tomorrow.copy(isLoading = true))
                LeagueDay.YESTERDAY -> it.copy(yesterday = it.yesterday.copy(isLoading = true))
            }
        }
        val date = when (leagueDay) {
            LeagueDay.TODAY -> {
                Calendar.getInstance().time
            }

            LeagueDay.TOMORROW -> {
                Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_MONTH, 1)
                }.time
            }

            LeagueDay.YESTERDAY -> {
                Calendar.getInstance().apply {
                    this.add(Calendar.DAY_OF_MONTH, -1)
                }.time
            }
        }
        val tResult = getMatchesUseCase(date)
        when (tResult) {
            is TResult.Error -> {
                _state.update {
                    when (leagueDay) {
                        LeagueDay.YESTERDAY -> it.copy(yesterday = it.yesterday.copy(error = tResult.exception))
                        LeagueDay.TODAY -> it.copy(today = it.today.copy(error = tResult.exception))
                        LeagueDay.TOMORROW -> it.copy(tomorrow = it.tomorrow.copy(error = tResult.exception))
                    }
                }
            }

            is TResult.Success -> {
                val matchesCount = tResult.data.map { it.matchId }.count()
                val map = tResult.data
                    .groupBy { it.leagueInfo }
                val leagueListWithMatches = map.keys.map {
                    LeaguesWithMatchesUIModel(
                        league = it,
                        matches = map[it]!!,
                    )
                }
                _state.update {
                    when (leagueDay) {
                        LeagueDay.TODAY -> it.copy(
                            today = it.today.copy(
                                matchCount = matchesCount,
                                leaguesWithMatchesUIModelList = leagueListWithMatches,
                                error = null
                            )
                        )

                        LeagueDay.TOMORROW -> {
                            it.copy(
                                tomorrow = it.tomorrow.copy(
                                    matchCount = matchesCount,
                                    leaguesWithMatchesUIModelList = leagueListWithMatches,
                                    error = null
                                )
                            )
                        }

                        LeagueDay.YESTERDAY -> {
                            it.copy(
                                yesterday = it.yesterday.copy(
                                    matchCount = matchesCount,
                                    leaguesWithMatchesUIModelList = leagueListWithMatches,
                                    error = null
                                )
                            )
                        }
                    }
                }

            }
        }
        _state.update {
            when (leagueDay) {
                LeagueDay.TODAY -> it.copy(today = it.today.copy(isLoading = false))
                LeagueDay.TOMORROW -> it.copy(tomorrow = it.tomorrow.copy(isLoading = false))
                LeagueDay.YESTERDAY -> it.copy(yesterday = it.yesterday.copy(isLoading = false))
            }
        }
    }

}