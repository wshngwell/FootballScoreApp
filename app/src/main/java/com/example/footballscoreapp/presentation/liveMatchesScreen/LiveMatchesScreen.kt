package com.example.footballscoreapp.presentation.liveMatchesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.footballscoreapp.presentation.MyProgressbar
import com.example.footballscoreapp.presentation.leagueScreen.AllLeaguesInfoCard
import com.example.footballscoreapp.presentation.leagueScreen.LeagueCard
import com.example.footballscoreapp.presentation.liveMatchesScreen.LiveMatchesViewModel.Event
import com.example.footballscoreapp.presentation.liveMatchesScreen.LiveMatchesViewModel.Intent
import com.example.footballscoreapp.presentation.liveMatchesScreen.LiveMatchesViewModel.State
import com.example.footballscoreapp.presentation.parseLoadingExceptionToStringResource
import com.example.footballscoreapp.ui.theme.myBackGround
import com.example.footballscoreapp.ui.theme.onLeagueColorContent
import com.example.footballscoreapp.ui.theme.screenTopPadding
import com.example.footballscoreapp.ui.theme.scrollBarColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel

@RootNavGraph
@Destination
@Composable
fun LiveMatchesScreen() {
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
                    //navigateToDetailedMatchScreen
                }
            }
        }
    }
    UI(
        state = state,
        intent = intent
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun UI(
    state: State = State(),
    intent: (Intent) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(myBackGround)
            .padding(top = screenTopPadding)
    ) {
        if (state.isLoading) {
            MyProgressbar(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            PullToRefreshBox(
                isRefreshing = false,
                onRefresh = { intent(Intent.LoadMatches) }
            ) {
                val listState = rememberLazyListState()
                LazyColumn(
                    modifier = Modifier
                        .drawWithContent {
                            drawContent()
                            val elementHeight =
                                this.size.height / listState.layoutInfo.totalItemsCount
                            val scrollbarOffsetY = listState.firstVisibleItemIndex * elementHeight
                            val scrollbarHeight =
                                listState.layoutInfo.visibleItemsInfo.size * elementHeight

                            drawRoundRect(
                                color = scrollBarColor,
                                topLeft = Offset(
                                    this.size.width - 5.dp.toPx(),
                                    scrollbarOffsetY
                                ),
                                cornerRadius = CornerRadius(3f, 3f),
                                size = Size(5.dp.toPx(), scrollbarHeight),
                            )
                        },
                    state = listState
                ) {
                    if (state.error != null) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxSize()
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = stringResource(state.error.parseLoadingExceptionToStringResource()),
                                    color = onLeagueColorContent,
                                )
                            }

                        }
                    } else {
                        item {
                            AllLeaguesInfoCard(
                                gamesCount = state.matchCount
                            )
                        }

                        items(
                            state.leaguesWithMatchesUIModelList,
                            key = { it.league.leagueId }) {
                            LeagueCard(
                                leagueEntity = it,
                                onMatchItemClicked = {
                                    intent(Intent.OnMatchClicked(it))
                                }
                            )
                        }
                    }

                }
            }
        }

    }
}