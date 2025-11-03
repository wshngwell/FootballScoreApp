package com.example.footballscoreapp.presentation.detailedMatchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.exoplayer.ExoPlayer
import com.example.footballscoreapp.R
import com.example.footballscoreapp.di.paramsForDetailViewModel
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.presentation.GsonUtil.fromJson
import com.example.footballscoreapp.presentation.GsonUtil.toJson
import com.example.footballscoreapp.presentation.MyProgressbar
import com.example.footballscoreapp.presentation.destinations.DetailsMatchScreenDestination
import com.example.footballscoreapp.presentation.detailedMatchScreen.DetailsMatchViewModel.Event
import com.example.footballscoreapp.presentation.detailedMatchScreen.DetailsMatchViewModel.Intent
import com.example.footballscoreapp.presentation.detailedMatchScreen.DetailsMatchViewModel.State
import com.example.footballscoreapp.presentation.myMatchEntityMock
import com.example.footballscoreapp.presentation.parseLoadingExceptionToStringResource
import com.example.footballscoreapp.presentation.teamDetailsScreen.getTeamDetailsDestination
import com.example.footballscoreapp.presentation.theme.categoriesInDetailsColor
import com.example.footballscoreapp.presentation.theme.lineUpCategorySize
import com.example.footballscoreapp.presentation.theme.noLineUpTextColor
import com.example.footballscoreapp.presentation.theme.textColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel


fun getDetailsMatchScreenDestination(matchEntity: MatchEntity) =
    DetailsMatchScreenDestination(
        matchEntity.toJson()
    )

@RootNavGraph
@Destination
@Composable
fun DetailsMatchScreen(
    matchEntityJson: String,
    navigator: DestinationsNavigator
) {
    val viewModel = koinViewModel<DetailsMatchViewModel>(parameters = {
        val matchEntity = matchEntityJson.fromJson<MatchEntity>()
        paramsForDetailViewModel(matchEntity, isTested = false)
    })
    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (Intent) -> Unit by remember {
        mutableStateOf(viewModel::sendIntent)
    }
    val event: Flow<Event> by remember {
        mutableStateOf(viewModel.event)
    }
    val player: ExoPlayer by remember {
        mutableStateOf(viewModel.player)
    }
    LaunchedEffect(Unit) {
        event.filterIsInstance<Event>().collect {
            when (it) {
                is Event.NavigateToTeamDetailsScreen -> navigator.navigate(
                    getTeamDetailsDestination(
                        it.teamMainInfoEntity
                    )
                ) {
                    launchSingleTop = true
                }
            }
        }
    }
    UI(
        state = state,
        intent = intent,
        player = player
    )

}

@Preview
@Composable
private fun UI(
    player: ExoPlayer = ExoPlayer.Builder(LocalContext.current).build(),
    state: State = State(
        matchEntity = myMatchEntityMock
    ),
    intent: (Intent) -> Unit = {}
) {
    var isVideoFullScreen by rememberSaveable {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (state.isLoading) {
            MyProgressbar(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (state.error != null) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(state.error.parseLoadingExceptionToStringResource()),
                color = textColor,
            )
        } else {
            DetailsScreenContent(
                player = player,
                state = state,
                isVideoFullScreen = isVideoFullScreen,
                updateIsVideoFullScreen = { isVideoFullScreen = isVideoFullScreen.not() },
                intent = intent
            )
        }
    }
}

@Preview
@Composable
private fun DetailsScreenContent(
    player: ExoPlayer = ExoPlayer.Builder(LocalContext.current).build(),
    state: State = State(matchEntity = myMatchEntityMock),
    isVideoFullScreen: Boolean = false,
    updateIsVideoFullScreen: () -> Unit = {},
    intent: (Intent) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (!isVideoFullScreen) {
            MatchAdditionalInfoCard(
                matchEntity = state.matchEntity,
                matchDetailInfoEntity = state.detailInfoEntity,
                onTeamIconClicked = {
                    intent(Intent.OnTeamIconClicked(it))
                }
            )
        }
        val scrollColumnModifier by remember(isVideoFullScreen) {
            mutableStateOf(
                if (!isVideoFullScreen) {
                    Modifier.verticalScroll(scrollState)
                } else Modifier
            )
        }
        Column(
            modifier = scrollColumnModifier
        ) {
            if (!isVideoFullScreen) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(categoriesInDetailsColor),
                    color = noLineUpTextColor,
                    textAlign = TextAlign.Center,
                    fontSize = lineUpCategorySize,
                    text = stringResource(R.string.match_highlights)
                )
            }
            FootballMediaPlayerCard(
                updateShouldControlsButtonsBeVisible = {
                    intent(Intent.UpdateShouldControlsButtonsBeVisible)
                },
                shouldControlsButtonsBeVisible = state.shouldControlsButtonsBeVisible,
                isPlaying = state.isPlaying,
                isBuffered = state.buffered,
                changeIsSliderDragging = {
                    intent(Intent.ChangeIsSliderDragging(it))
                },
                currentPositionOfSlider = state.currentPositionOfSlider,
                playingStateChange = { intent(Intent.PlayingStateChange) },
                updateCurrentPositionOfSlider = {
                    intent(Intent.UpdateCurrentPositionOfSlider(it))
                },
                videoDuration = state.videoDuration,
                mediaPlayer = player,
                updateIsVideoFullScreen = updateIsVideoFullScreen
            )
            if (!isVideoFullScreen) {
                if (state.detailInfoEntity?.lineUpEntity == null) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(categoriesInDetailsColor),
                        color = noLineUpTextColor,
                        textAlign = TextAlign.Center,
                        fontSize = lineUpCategorySize,
                        text = stringResource(R.string.noLineUp)
                    )
                } else {
                    TeamsLineUps(
                        matchAdditionalInfo = state.detailInfoEntity.matchAdditionalInfoEntity,
                        lineUpEntity = state.detailInfoEntity.lineUpEntity
                    )
                }
                if (state.detailInfoEntity?.teamStatisticsEntity == null) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(categoriesInDetailsColor),
                        color = noLineUpTextColor,
                        textAlign = TextAlign.Center,
                        fontSize = lineUpCategorySize,
                        text = stringResource(R.string.noStatistic)
                    )
                } else {
                    TeamsStatistics(
                        teamStatisticsEntity = state.detailInfoEntity.teamStatisticsEntity
                    )
                }
            }
        }
    }
}