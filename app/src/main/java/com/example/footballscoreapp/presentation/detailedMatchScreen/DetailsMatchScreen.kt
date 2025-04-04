package com.example.footballscoreapp.presentation.detailedMatchScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.presentation.GsonUtil.fromJson
import com.example.footballscoreapp.presentation.GsonUtil.toJson
import com.example.footballscoreapp.presentation.detailedMatchScreen.DetailsMatchViewModel.Event
import com.example.footballscoreapp.presentation.detailedMatchScreen.DetailsMatchViewModel.Intent
import com.example.footballscoreapp.presentation.detailedMatchScreen.DetailsMatchViewModel.State
import com.example.footballscoreapp.presentation.myMatchEntityMock
import com.example.footballscoreapp.utils.myLog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


fun getMatchEntityJson(matchEntity: MatchEntity): String {
    return matchEntity.toJson()
}

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
}