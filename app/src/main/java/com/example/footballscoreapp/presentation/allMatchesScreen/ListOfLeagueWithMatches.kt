package com.example.footballscoreapp.presentation.allMatchesScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.presentation.MyProgressbar
import com.example.footballscoreapp.presentation.myVerticalScrollBar
import com.example.footballscoreapp.presentation.parseLoadingExceptionToStringResource
import com.example.footballscoreapp.presentation.theme.textColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfLeagueWithMatches(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    loadMatches: () -> Unit = {},
    onMatchClicked: (MatchEntity) -> Unit = {},
    onAddOrDeleteMatchFromFavouriteClicked: (MatchEntity) -> Unit = {},
    error: LoadingException? = null,
    matchCount: Int = 0,
    leaguesWithMatchesUIModelList: List<LeaguesWithMatchesUIModel> = listOf(),
    onExpanded: (LeaguesWithMatchesUIModel) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (isLoading) {
            MyProgressbar(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            PullToRefreshBox(
                isRefreshing = false,
                onRefresh = { loadMatches() }
            ) {
                val listState = rememberLazyListState()
                LazyColumn(
                    modifier = Modifier
                        .myVerticalScrollBar(listState),
                    state = listState
                ) {
                    if (error != null) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxSize()
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = stringResource(error.parseLoadingExceptionToStringResource()),
                                    color = textColor,
                                )
                            }

                        }
                    } else {
                        item {
                            AllLeaguesInfoCard(
                                gamesCount = matchCount
                            )
                        }

                        items(
                            leaguesWithMatchesUIModelList,
                            key = { it.copy(isExpanded = false).toString() }) {
                            LeagueCard(
                                leagueWithMatchUIModel = it,
                                onMatchItemClicked = {
                                    onMatchClicked(it)
                                },
                                onAddOrDeleteMatchFromFavouriteClicked = onAddOrDeleteMatchFromFavouriteClicked,
                                onExpanded = { onExpanded(it) }
                            )
                        }
                    }

                }
            }
        }

    }
}