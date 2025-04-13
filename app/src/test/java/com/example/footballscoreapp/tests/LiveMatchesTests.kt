package com.example.footballscoreapp.tests

import com.example.footballscoreapp.ApiServiceMock.Companion.FAVOURITE_MATCHES_COUNT
import com.example.footballscoreapp.ApiServiceMock.Companion.FAVOURITE_MATCHES_COUNT_AFTER_ADDING
import com.example.footballscoreapp.ApiServiceMock.Companion.FAVOURITE_MATCHES_COUNT_AFTER_DELETING
import com.example.footballscoreapp.ApiServiceMock.Companion.LIVE_MATCHES_COUNT
import com.example.footballscoreapp.ApiServiceMock.Companion.LIVE_MATCHES_FAVOURITE_COUNT
import com.example.footballscoreapp.ApiServiceMock.Companion.LIVE_MATCHES_FAVOURITE_COUNT_AFTER_ADDING
import com.example.footballscoreapp.ApiServiceMock.Companion.LIVE_MATCHES_FAVOURITE_COUNT_AFTER_DELETING
import com.example.footballscoreapp.BaseTestClass
import com.example.footballscoreapp.presentation.liveMatchesScreen.LiveMatchesViewModel
import com.example.footballscoreapp.tests.AllLeaguesAndMatchesTests.Companion.myAddToDbMatchMock
import com.example.footballscoreapp.tests.AllLeaguesAndMatchesTests.Companion.myDeleteMatchToDbMock
import kotlinx.coroutines.delay
import org.junit.Test
import org.koin.core.component.get
import kotlin.test.assertEquals

class LiveMatchesTests : BaseTestClass() {
    @Test
    fun testLiveMatchesScreen() {
        test {
            val viewModel = get<LiveMatchesViewModel>()
            delay(200)
            assertEquals(viewModel.state.value.matchCount, LIVE_MATCHES_COUNT)
            viewModel.sendIntent(LiveMatchesViewModel.Intent.LoadMatches)
            delay(200)
            assertEquals(viewModel.state.value.matchCount, LIVE_MATCHES_COUNT)
        }
    }

    @Test
    fun testAddMatchToFavouriteInLiveScreen() {
        test {
            val viewModel = get<LiveMatchesViewModel>()
            delay(200)
            assertEquals(viewModel.state.value.favouriteMatchesList.size, FAVOURITE_MATCHES_COUNT)
            viewModel.sendIntent(
                LiveMatchesViewModel.Intent.OnAddOrDeleteMatchFromFavouriteClicked(
                    myAddToDbMatchMock
                )
            )
            delay(200)
            assertEquals(
                viewModel.state.value.favouriteMatchesList.size,
                FAVOURITE_MATCHES_COUNT_AFTER_ADDING
            )
        }
    }

    @Test
    fun testDeleteMatchToFavouriteInLiveScreen() {
        test {
            val viewModel = get<LiveMatchesViewModel>()
            delay(200)
            assertEquals(viewModel.state.value.favouriteMatchesList.size, FAVOURITE_MATCHES_COUNT)
            viewModel.sendIntent(
                LiveMatchesViewModel.Intent.OnAddOrDeleteMatchFromFavouriteClicked(
                    myDeleteMatchToDbMock
                )
            )
            delay(200)
            assertEquals(
                viewModel.state.value.favouriteMatchesList.size,
                FAVOURITE_MATCHES_COUNT_AFTER_DELETING
            )

        }
    }

    @Test
    fun testMatchCorrectStatusInLiveScreen() {
        test {
            val viewModel = get<LiveMatchesViewModel>()
            delay(200)

            val favouriteMatches =
                viewModel.state.value.leaguesWithMatchesUIModelListWithFavourite.count {
                    it.matches.any { it.isFavourite }
                }
            assertEquals(favouriteMatches, LIVE_MATCHES_FAVOURITE_COUNT)
            viewModel.sendIntent(
                LiveMatchesViewModel.Intent.OnAddOrDeleteMatchFromFavouriteClicked(
                    myAddToDbMatchMock
                )
            )
            delay(200)
            val secondFavouriteMatches =
                viewModel.state.value.leaguesWithMatchesUIModelListWithFavourite.count {
                    it.matches.any { it.isFavourite }
                }
            assertEquals(secondFavouriteMatches, LIVE_MATCHES_FAVOURITE_COUNT_AFTER_ADDING)
            viewModel.sendIntent(
                LiveMatchesViewModel.Intent.OnAddOrDeleteMatchFromFavouriteClicked(
                    myDeleteMatchToDbMock
                )
            )
            delay(200)
            val thirdFavouriteMatches =
                viewModel.state.value.leaguesWithMatchesUIModelListWithFavourite.count {
                    it.matches.any { it.isFavourite }
                }

            assertEquals(thirdFavouriteMatches, LIVE_MATCHES_FAVOURITE_COUNT_AFTER_DELETING)

        }
    }
}