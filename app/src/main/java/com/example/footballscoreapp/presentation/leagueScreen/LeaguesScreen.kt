package com.example.footballscoreapp.presentation.leagueScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.footballscoreapp.R
import com.example.footballscoreapp.presentation.ListOfLeagueWithMatches
import com.example.footballscoreapp.presentation.leagueScreen.LeaguesViewModel.Event
import com.example.footballscoreapp.presentation.leagueScreen.LeaguesViewModel.Intent
import com.example.footballscoreapp.presentation.leagueScreen.LeaguesViewModel.State
import com.example.footballscoreapp.ui.theme.firstColorOfLeagueCardBackGround
import com.example.footballscoreapp.ui.theme.myBackGround
import com.example.footballscoreapp.ui.theme.onLeagueColorContent
import com.example.footballscoreapp.ui.theme.screenTopPadding
import com.example.footballscoreapp.ui.theme.secondColorOfLeagueCardBackGround
import com.example.footballscoreapp.ui.theme.textPadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.absoluteValue

@RootNavGraph(start = true)
@Destination
@Composable
fun LeaguesScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<LeaguesViewModel>()
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

@Preview
@Composable
private fun UI(
    state: State = State(),
    intent: (Intent) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = state.currentLeagueDay.ordinal,
        pageCount = { state.leagueDayLists.size })
    val scrollCoroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        intent(Intent.ChangeCurrentDay(LeagueDay.entries[pagerState.currentPage]))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(myBackGround)
            .padding(
                top = screenTopPadding,
            )
    ) {
        TabRow(
            modifier = Modifier
                .fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                )
            },
        ) {
            state.leagueDayLists.forEach { day ->
                val currentText = when (day) {
                    LeagueDay.TODAY -> stringResource(R.string.today)
                    LeagueDay.TOMORROW -> stringResource(R.string.tomorrow)
                    LeagueDay.YESTERDAY -> stringResource(R.string.yesterday)
                }
                Text(
                    text = currentText,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            scrollCoroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    page = day.ordinal
                                )
                            }
                        }
                        .background(
                            color = if (pagerState.currentPage == day.ordinal)
                                firstColorOfLeagueCardBackGround else secondColorOfLeagueCardBackGround
                        )
                        .padding(textPadding),
                    color = onLeagueColorContent,
                    textAlign = TextAlign.Center
                )
            }
        }
        HorizontalPager(state = pagerState) { page ->

            val leagueDayState = when (state.leagueDayLists[page]) {
                LeagueDay.TODAY -> state.today
                LeagueDay.TOMORROW -> state.tomorrow
                LeagueDay.YESTERDAY -> state.yesterday
            }
            PagerCard(
                leagueDayState = leagueDayState,
                pagerState = pagerState,
                page = page,
                intent = intent
            )

        }
    }
}

@Composable
private fun PagerCard(
    leagueDayState: LeaguesViewModel.DayState = LeaguesViewModel.DayState(),
    pagerState: PagerState,
    page: Int = 0,
    intent: (Intent) -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(myBackGround)
            .graphicsLayer {
                val pageOffset = (
                        pagerState.currentPage - page
                        ).toFloat() + pagerState.currentPageOffsetFraction
                alpha = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
            },

        ) {
        ListOfLeagueWithMatches(
            isLoading = leagueDayState.isLoading,
            loadMatches = { intent(Intent.LoadLeagues) },
            onMatchClicked = { intent(Intent.OnMatchClicked(it)) },
            error = leagueDayState.error,
            matchCount = leagueDayState.matchCount,
            leaguesWithMatchesUIModelList = leagueDayState.leaguesWithMatchesUIModelList
        )
    }

}