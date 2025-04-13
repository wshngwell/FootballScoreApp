package com.example.footballscoreapp.presentation.leagueScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballscoreapp.domain.SingleFlowEvent
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.domain.usecases.allMatchesUseCases.GetMatchesUseCase
import com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases.AddMatchToFavouriteUseCase
import com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases.DeleteMatchFromFavouriteUseCase
import com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases.GetFavouriteMatchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class LeaguesViewModel(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val getFavouriteMatchesUseCase: GetFavouriteMatchesUseCase,
    private val addMatchToFavouriteUseCase: AddMatchToFavouriteUseCase,
    private val deleteMatchFromFavouriteUseCase: DeleteMatchFromFavouriteUseCase
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
        val favouriteMatchesList: List<MatchEntity> = listOf(),
        val error: LoadingException? = null
    ) {
        val leaguesWithMatchesUIModelListWithFavourite =
            leaguesWithMatchesUIModelList.map { leaguesWithMatchesUIModel ->
                leaguesWithMatchesUIModel.copy(
                    matches = leaguesWithMatchesUIModel.matches.map { match ->
                        val favouriteMatchIds = favouriteMatchesList.map { it.matchId }
                        if (favouriteMatchIds.contains(match.matchId)) {
                            match.copy(isFavourite = true)
                        } else {
                            match.copy(isFavourite = false)
                        }
                    }
                )
            }
    }

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    sealed interface Intent {
        data class ChangeCurrentDay(val leagueDay: LeagueDay) : Intent
        data object LoadLeagues : Intent
        data class OnMatchClicked(val matchEntity: MatchEntity) : Intent
        data class OnAddOrDeleteMatchFromFavouriteClicked(val matchEntity: MatchEntity) : Intent
        data class OnExpandedLeague(val leagueWithMatchUiModel: LeaguesWithMatchesUIModel) : Intent
    }

    sealed interface Event {
        data class OnNavigateToDetailedMatchesScreen(val matchEntity: MatchEntity) : Event
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.OnAddOrDeleteMatchFromFavouriteClicked -> {
                viewModelScope.launch {
                    val favouriteListIds = state.value.today.favouriteMatchesList.map { it.matchId }
                    if (favouriteListIds.contains(intent.matchEntity.matchId)
                    ) {
                        deleteMatchFromFavouriteUseCase(listOf(intent.matchEntity.matchId))
                    } else {
                        addMatchToFavouriteUseCase(listOf(intent.matchEntity))
                    }
                }

            }

            is Intent.ChangeCurrentDay -> {
                _state.update { it.copy(currentLeagueDay = intent.leagueDay) }
            }

            is Intent.LoadLeagues -> {
                launchLoadLeagues()
            }

            is Intent.OnMatchClicked -> _event.emit(Event.OnNavigateToDetailedMatchesScreen(intent.matchEntity))
            is Intent.OnExpandedLeague -> {
                _state.update {
                    when (state.value.currentLeagueDay) {
                        LeagueDay.YESTERDAY -> {
                            it.copy(
                                yesterday = it.yesterday.copy(
                                    leaguesWithMatchesUIModelList = updateLeaguesWithMatchesUIModelList(
                                        dayState = it.yesterday,
                                        leagueWithMatchUiModel = intent.leagueWithMatchUiModel
                                    )
                                )
                            )
                        }

                        LeagueDay.TODAY -> {
                            it.copy(
                                today = it.today.copy(
                                    leaguesWithMatchesUIModelList = updateLeaguesWithMatchesUIModelList(
                                        dayState = it.today,
                                        leagueWithMatchUiModel = intent.leagueWithMatchUiModel
                                    )
                                )
                            )
                        }

                        LeagueDay.TOMORROW -> {
                            it.copy(
                                tomorrow = it.tomorrow.copy(
                                    leaguesWithMatchesUIModelList = updateLeaguesWithMatchesUIModelList(
                                        dayState = it.tomorrow,
                                        leagueWithMatchUiModel = intent.leagueWithMatchUiModel
                                    )
                                )
                            )
                        }
                    }

                }

            }
        }
    }

    private fun updateLeaguesWithMatchesUIModelList(
        dayState: DayState,
        leagueWithMatchUiModel: LeaguesWithMatchesUIModel,
    ): List<LeaguesWithMatchesUIModel> {
        return dayState.leaguesWithMatchesUIModelList.map {
            if (it.league.leagueId == leagueWithMatchUiModel.league.leagueId) {
                it.copy(isExpanded = it.isExpanded.not())
            } else it
        }
    }

    init {
        viewModelScope.launch {
            getFavouriteMatchesUseCase().collect { favouriteMatchList ->
                println(favouriteMatchList)
                _state.update {
                    it.copy(
                        today = it.today.copy(favouriteMatchesList = favouriteMatchList),
                        yesterday = it.yesterday.copy(favouriteMatchesList = favouriteMatchList),
                        tomorrow = it.tomorrow.copy(favouriteMatchesList = favouriteMatchList)
                    )
                }
            }
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