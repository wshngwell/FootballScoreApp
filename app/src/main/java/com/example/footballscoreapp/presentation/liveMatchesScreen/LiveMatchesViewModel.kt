package com.example.footballscoreapp.presentation.liveMatchesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballscoreapp.domain.SingleFlowEvent
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases.AddMatchToFavouriteUseCase
import com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases.DeleteMatchFromFavouriteUseCase
import com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases.GetFavouriteMatchesUseCase
import com.example.footballscoreapp.domain.usecases.liveMatchesUseCases.GetLiveMatchesUseCase
import com.example.footballscoreapp.presentation.leagueScreen.LeaguesWithMatchesUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class LiveMatchesViewModel(
    private val getLiveMatchesUseCase: GetLiveMatchesUseCase,
    private val getFavouriteMatchesUseCase: GetFavouriteMatchesUseCase,
    private val addMatchToFavouriteUseCase: AddMatchToFavouriteUseCase,
    private val deleteMatchFromFavouriteUseCase: DeleteMatchFromFavouriteUseCase
) : ViewModel() {

    data class State(
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
        data object LoadMatches : Intent
        data class OnMatchClicked(val matchEntity: MatchEntity) : Intent
        data class OnAddOrDeleteMatchFromFavouriteClicked(val matchEntity: MatchEntity) : Intent
        data class OnExpandedLeague(val leagueWithMatchUIModel: LeaguesWithMatchesUIModel) : Intent
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

            is Intent.OnAddOrDeleteMatchFromFavouriteClicked -> {
                viewModelScope.launch {
                    val favouriteListIds = state.value.favouriteMatchesList.map { it.matchId }
                    if (favouriteListIds.contains(intent.matchEntity.matchId)
                    ) {
                        deleteMatchFromFavouriteUseCase(listOf(intent.matchEntity.matchId))
                    } else {
                        addMatchToFavouriteUseCase(listOf(intent.matchEntity))
                    }
                }
            }

            is Intent.OnExpandedLeague -> _state.update {
                it.copy(
                    leaguesWithMatchesUIModelList = it.leaguesWithMatchesUIModelList.map {
                        if (it.league.leagueId == intent.leagueWithMatchUIModel.league.leagueId) {
                            it.copy(isExpanded = it.isExpanded.not())
                        } else it
                    }
                )
            }
        }
    }

    init {
        viewModelScope.launch {
            getFavouriteMatchesUseCase().collect { favouriteMatchList ->
                _state.update { it.copy(favouriteMatchesList = favouriteMatchList) }
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
        val tResult = getLiveMatchesUseCase(date)
        when (tResult) {
            is TResult.Error -> {
                _state.update { it.copy(error = tResult.exception) }
            }

            is TResult.Success -> {
                val matchCount = tResult.data.count()
                val map = tResult.data.groupBy {
                    it.leagueInfo
                }
                val leagueListWithMatches = map.keys.map {
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