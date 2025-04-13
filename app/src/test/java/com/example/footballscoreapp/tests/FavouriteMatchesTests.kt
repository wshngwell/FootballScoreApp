package com.example.footballscoreapp.tests

import com.example.footballscoreapp.ApiServiceMock.Companion.FAVOURITE_MATCHES_COUNT
import com.example.footballscoreapp.ApiServiceMock.Companion.FAVOURITE_MATCHES_COUNT_AFTER_ADDING
import com.example.footballscoreapp.BaseTestClass
import com.example.footballscoreapp.presentation.favouriteMatchesScreen.FavouriteMatchesViewModel
import com.example.footballscoreapp.tests.AllLeaguesAndMatchesTests.Companion.myAddToDbMatchMock
import kotlinx.coroutines.delay
import org.junit.Test
import org.koin.core.component.get
import kotlin.test.assertEquals

class FavouriteMatchesTests : BaseTestClass() {

    @Test
    fun testFavouriteScreen() {
        test {
            val viewModel = get<FavouriteMatchesViewModel>()
            delay(200)
            assertEquals(viewModel.state.value.favouriteMatchesList.size, FAVOURITE_MATCHES_COUNT)
        }
    }

    @Test
    fun addMatchOnFavouriteScreen() {
        test {
            val viewModel = get<FavouriteMatchesViewModel>()
            delay(200)
            assertEquals(viewModel.state.value.favouriteMatchesList.size, FAVOURITE_MATCHES_COUNT)
            viewModel.sendIntent(
                FavouriteMatchesViewModel.Intent.OnAddOrDeleteMatchFromFavouriteClicked(
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
}