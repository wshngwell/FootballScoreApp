package com.example.footballscoreapp.presentation.liveMatchesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.footballscoreapp.presentation.allMatchesScreen.ListOfLeagueWithMatches
import com.example.footballscoreapp.presentation.detailedMatchScreen.getDetailsMatchScreenDestination
import com.example.footballscoreapp.presentation.liveMatchesScreen.LiveMatchesViewModel.Event
import com.example.footballscoreapp.presentation.liveMatchesScreen.LiveMatchesViewModel.Intent
import com.example.footballscoreapp.presentation.liveMatchesScreen.LiveMatchesViewModel.State
import com.example.footballscoreapp.presentation.theme.myBackGround
import com.example.footballscoreapp.presentation.theme.screenTopPadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel

@RootNavGraph
@Destination
@Composable
fun LiveMatchesScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = koinViewModel<LiveMatchesViewModel>()
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
                is Event.OnNavigateToDetailedMatchesScreen -> {
                    navigator.navigate(getDetailsMatchScreenDestination(it.matchEntity)) {
                        launchSingleTop = true
                    }
                }
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
    state: State = State(),
    intent: (Intent) -> Unit = {}
) {
    ListOfLeagueWithMatches(
        modifier = Modifier
            .background(myBackGround)
            .padding(top = screenTopPadding),
        isLoading = state.isLoading,
        loadMatches = { intent(Intent.LoadMatches) },
        onMatchClicked = { intent(Intent.OnMatchClicked(it)) },
        error = state.error,
        matchCount = state.matchCount,
        leaguesWithMatchesUIModelList = state.leaguesWithMatchesUIModelListWithFavourite,
        onAddOrDeleteMatchFromFavouriteClicked = {
            intent(
                Intent.OnAddOrDeleteMatchFromFavouriteClicked(
                    it
                )
            )
        },
        onExpanded = {
            intent(Intent.OnExpandedLeague(it))
        }
    )
}