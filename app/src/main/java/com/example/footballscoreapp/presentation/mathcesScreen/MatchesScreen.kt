package com.example.footballscoreapp.presentation.mathcesScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.footballscoreapp.domain.entities.LeagueEntity
import com.example.footballscoreapp.presentation.GsonUtil.fromJson
import com.example.footballscoreapp.presentation.mathcesScreen.MatchesViewModel.Event
import com.example.footballscoreapp.presentation.mathcesScreen.MatchesViewModel.Intent
import com.example.footballscoreapp.presentation.mathcesScreen.MatchesViewModel.State
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@RootNavGraph
@Destination
@Composable
fun MatchesScreen(
    leagueEntityGson: String
) {

    val viewModel = koinViewModel<MatchesViewModel>(parameters = {
        parametersOf(
            leagueEntityGson.fromJson()
        )
    })

    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (Intent) -> Unit by remember {
        mutableStateOf(viewModel::sendIntent)
    }
    val event: Flow<Event> by remember {
        mutableStateOf(viewModel.event)
    }

}

@Preview
@Composable
private fun UI(
    state: State = State(leagueEntity = LeagueEntity("", "", "")),
    intent: (Intent) -> Unit = {}
) {

}