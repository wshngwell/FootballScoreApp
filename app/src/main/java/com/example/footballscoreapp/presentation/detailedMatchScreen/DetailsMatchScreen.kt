package com.example.footballscoreapp.presentation.detailedMatchScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.footballscoreapp.R
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
import com.example.footballscoreapp.ui.theme.lineUpCategorySize
import com.example.footballscoreapp.ui.theme.noLineUpTextColor
import com.example.footballscoreapp.ui.theme.onLeagueColorContent
import com.example.footballscoreapp.utils.myLog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


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
        parametersOf(matchEntity)
    })
    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (Intent) -> Unit by remember {
        mutableStateOf(viewModel::sendIntent)
    }
    val event: Flow<Event> by remember {
        mutableStateOf(viewModel.event)
    }
    LaunchedEffect(Unit) {
        event.filterIsInstance<Event>().collect {
            when (it) {
                is Event.NavigateToPlayerDetailsScreen -> TODO()
            }
        }
    }
    UI(
        state = state,
        intent = intent
    )

}

@Preview
@Composable
private fun UI(
    state: State = State(matchEntity = myMatchEntityMock),
    intent: (Intent) -> Unit = {}
) {
    SideEffect {
        myLog(state.detailInfoEntity.toString())
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
                color = onLeagueColorContent,
            )
        } else {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                MatchAdditionalInfoCard(
                    matchEntity = state.matchEntity,
                    matchDetailInfoEntity = state.detailInfoEntity
                )
                Column(
                    modifier = Modifier.verticalScroll(scrollState)
                ) {
                    if (state.detailInfoEntity?.lineUpEntity == null) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
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
                            modifier = Modifier.fillMaxWidth(),
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
}