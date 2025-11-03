package com.example.footballscoreapp.tests

import com.example.footballscoreapp.ApiServiceMock.Companion.LINE_UP_LOADED_ID
import com.example.footballscoreapp.ApiServiceMock.Companion.LINE_UP_PLAYERS_SIZE
import com.example.footballscoreapp.ApiServiceMock.Companion.STATISTICS_SIZE
import com.example.footballscoreapp.BaseTestClass
import com.example.footballscoreapp.di.paramsForDetailViewModel
import com.example.footballscoreapp.presentation.detailedMatchScreen.DetailsMatchViewModel
import com.example.footballscoreapp.tests.AllLeaguesAndMatchesTests.Companion.myAddToDbMatchMock
import kotlinx.coroutines.delay
import org.junit.Test
import org.koin.core.component.get
import kotlin.test.assertEquals

class DetailsMatchTests : BaseTestClass() {

    @Test
    fun testDetailMatchScreen() {
        test {
            val viewModel = get<DetailsMatchViewModel>(
                parameters = {
                    paramsForDetailViewModel(
                        matchEntity = myAddToDbMatchMock,
                        isTested = true
                    )
                }
            )
            delay(200)
            assertEquals(
                viewModel.state.value.detailInfoEntity?.matchAdditionalInfoEntity?.lineupsId,
                LINE_UP_LOADED_ID
            )
            assertEquals(
                viewModel.state.value.detailInfoEntity?.lineUpEntity?.awayTeam?.players?.size,
                LINE_UP_PLAYERS_SIZE
            )
            assertEquals(
                viewModel.state.value.detailInfoEntity?.teamStatisticsEntity?.size, STATISTICS_SIZE
            )
        }
    }
}