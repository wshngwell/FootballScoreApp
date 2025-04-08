package com.example.footballscoreapp.presentation.favouriteMatchesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.footballscoreapp.presentation.detailedMatchScreen.getDetailsMatchScreenDestination
import com.example.footballscoreapp.presentation.favouriteMatchesScreen.FavouriteMatchesViewModel.Event
import com.example.footballscoreapp.presentation.favouriteMatchesScreen.FavouriteMatchesViewModel.Intent
import com.example.footballscoreapp.presentation.favouriteMatchesScreen.FavouriteMatchesViewModel.State
import com.example.footballscoreapp.presentation.mathcesUi.MatchCard
import com.example.footballscoreapp.ui.theme.myBackGround
import com.example.footballscoreapp.ui.theme.paddingCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel

@RootNavGraph
@Destination
@Composable
fun FavouriteMatchesScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = koinViewModel<FavouriteMatchesViewModel>()
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
                    navigator.navigate(getDetailsMatchScreenDestination(it.matchEntity))
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
    intent: (Intent) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(myBackGround)

    ) {
        LazyColumn {
            items(state.favouriteMatchesList, key = { it.toString() }) {
                MatchCard(
                    modifier = Modifier
                        .animateItem()
                        .fillMaxWidth()
                        .padding(top = paddingCard, bottom = paddingCard),
                    matchEntity = it,
                    onMatchCardClicked = { intent(Intent.OnMatchClicked(it)) },
                    onAddOrDeleteMatchFromFavouriteClicked = {
                        intent(
                            Intent.OnAddOrDeleteMatchFromFavouriteClicked(it)
                        )
                    }
                )
            }
        }
    }
}