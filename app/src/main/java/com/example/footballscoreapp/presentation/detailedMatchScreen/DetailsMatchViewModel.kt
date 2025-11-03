package com.example.footballscoreapp.presentation.detailedMatchScreen

import android.app.Application
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.footballscoreapp.domain.SingleFlowEvent
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.entities.detailMatchInfo.MatchDetailInfoEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.lineup.FootballPlayerEntity
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.domain.entities.teams.TeamMainInfoEntity
import com.example.footballscoreapp.domain.usecases.detailMatchInfoUseCases.GetDetailedMatchInfoUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class DetailsMatchViewModel(
    application: Application,
    private val getDetailedMatchInfoUseCase: GetDetailedMatchInfoUseCase,
    matchEntity: MatchEntity,
    val isTested: Boolean
) : ViewModel() {

    private val randomUrl =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    data class State(
        val matchEntity: MatchEntity,
        val detailInfoEntity: MatchDetailInfoEntity? = null,
        val isLoading: Boolean = false,
        val error: LoadingException? = null,

        val isPlaying: Boolean = false,
        val currentPositionOfSlider: Long = 0L,
        val isSliderDragging: Boolean = false,
        val buffered: Boolean = false,
        val videoDuration: Long = 1L,
        val shouldControlsButtonsBeVisible: Boolean = true
    )

    private val _state = MutableStateFlow(
        State(matchEntity = matchEntity)
    )

    val player by lazy {
        ExoPlayer.Builder(application).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(randomUrl)))
            prepare()
        }
    }

    val state = _state.asStateFlow()

    sealed interface Intent {
        data object PlayingStateChange : Intent
        data class ChangeIsSliderDragging(val value: Boolean) : Intent
        data object UpdateShouldControlsButtonsBeVisible : Intent
        data class UpdateCurrentPositionOfSlider(val value: Long) : Intent
        data class OnSoccerPlayerClicked(val footballPlayerEntity: FootballPlayerEntity) : Intent
        data class OnTeamIconClicked(val teamMainInfoEntity: TeamMainInfoEntity) : Intent
    }

    sealed interface Event {
        data class NavigateToTeamDetailsScreen(val teamMainInfoEntity: TeamMainInfoEntity) :
            Event
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    init {
        if (!isTested) {
            viewModelScope.launch {
                while (isActive) {
                    if (!state.value.isSliderDragging) {
                        _state.update {
                            it.copy(currentPositionOfSlider = player.currentPosition)
                        }
                    }
                    delay(200)
                }
            }
            player.addListener(object : Player.Listener {
                override fun onEvents(
                    player: Player,
                    events: Player.Events
                ) {
                    super.onEvents(player, events)
                    _state.update {
                        it.copy(
                            videoDuration = player.duration.coerceAtLeast(1L),
                            buffered = player.playbackState == Player.STATE_BUFFERING,
                            isPlaying = player.isPlaying
                        )
                    }
                }
            })
        }

    }

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val detailInfoTresult = getDetailedMatchInfoUseCase(state.value.matchEntity.matchId)
            when (detailInfoTresult) {
                is TResult.Error -> {
                    _state.update { it.copy(error = detailInfoTresult.exception) }
                }

                is TResult.Success -> {
                    val filteredHomePlayersList =
                        detailInfoTresult.data.lineUpEntity?.homeTeam?.players?.sortedBy { !it.substitute }
                            .orEmpty()
                    val filteredAwayPlayersList =
                        detailInfoTresult.data.lineUpEntity?.awayTeam?.players?.sortedBy { !it.substitute }
                            .orEmpty()
                    val filteredLineUpEntity = detailInfoTresult.data.lineUpEntity?.copy(
                        awayTeam = detailInfoTresult.data.lineUpEntity.awayTeam.copy(
                            players = filteredAwayPlayersList
                        ),
                        homeTeam = detailInfoTresult.data.lineUpEntity.homeTeam.copy(
                            players = filteredHomePlayersList
                        ),
                    )
                    _state.update {
                        it.copy(
                            detailInfoEntity = detailInfoTresult.data.copy(lineUpEntity = filteredLineUpEntity)
                        )
                    }
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.OnSoccerPlayerClicked -> {
                //emit event
            }

            is Intent.ChangeIsSliderDragging -> {
                if (!intent.value) player.seekTo(state.value.currentPositionOfSlider)
                _state.update { it.copy(isSliderDragging = intent.value) }
            }

            Intent.PlayingStateChange -> {
                state.value.run { if (isPlaying) player.pause() else player.play() }
            }

            is Intent.UpdateCurrentPositionOfSlider -> _state.update {
                it.copy(currentPositionOfSlider = intent.value)
            }

            is Intent.UpdateShouldControlsButtonsBeVisible -> _state.update {
                it.copy(
                    shouldControlsButtonsBeVisible = !state.value.shouldControlsButtonsBeVisible
                )
            }

            is Intent.OnTeamIconClicked -> _event.emit(Event.NavigateToTeamDetailsScreen(intent.teamMainInfoEntity))
        }
    }

    override fun onCleared() {
        player.release()
        super.onCleared()
    }
}