package com.example.footballscoreapp.tests

import com.example.footballscoreapp.ApiServiceMock.Companion.FAVOURITE_MATCHES_COUNT
import com.example.footballscoreapp.ApiServiceMock.Companion.FAVOURITE_MATCHES_COUNT_AFTER_ADDING
import com.example.footballscoreapp.ApiServiceMock.Companion.FAVOURITE_MATCHES_COUNT_AFTER_DELETING
import com.example.footballscoreapp.ApiServiceMock.Companion.MATCHES_COUNT
import com.example.footballscoreapp.BaseTestClass
import com.example.footballscoreapp.domain.entities.matches.LeagueEntity
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.domain.entities.matches.MatchStatusEntity
import com.example.footballscoreapp.domain.entities.matches.TeamMatchInfoEntity
import com.example.footballscoreapp.presentation.AllMatchesScreen.AllLeaguesWithMatchesViewModel
import kotlinx.coroutines.delay
import org.junit.Test
import org.koin.core.component.get
import java.util.Calendar
import kotlin.test.assertEquals

class AllLeaguesAndMatchesTests : BaseTestClass() {

    @Test
    fun testsMatchesAndLeagues() {
        test {
            val viewModel = get<AllLeaguesWithMatchesViewModel>()
            delay(200)
            assertEquals(viewModel.state.value.today.matchCount, MATCHES_COUNT)
            assertEquals(viewModel.state.value.yesterday.matchCount, MATCHES_COUNT)
            assertEquals(viewModel.state.value.tomorrow.matchCount, MATCHES_COUNT)
            assertEquals(
                viewModel.state.value.tomorrow.leaguesWithMatchesUIModelList.size,
                MATCHES_COUNT
            )
            assertEquals(
                viewModel.state.value.yesterday.leaguesWithMatchesUIModelList.size,
                MATCHES_COUNT
            )
            assertEquals(
                viewModel.state.value.today.leaguesWithMatchesUIModelList.size,
                MATCHES_COUNT
            )
            assertEquals(
                viewModel.state.value.today.favouriteMatchesList.size,
                FAVOURITE_MATCHES_COUNT
            )
            assertEquals(
                viewModel.state.value.yesterday.favouriteMatchesList.size,
                FAVOURITE_MATCHES_COUNT
            )
            assertEquals(
                viewModel.state.value.tomorrow.favouriteMatchesList.size,
                FAVOURITE_MATCHES_COUNT
            )
            viewModel.sendIntent(AllLeaguesWithMatchesViewModel.Intent.LoadLeagues)
            delay(200)
            assertEquals(viewModel.state.value.today.matchCount, MATCHES_COUNT)
            assertEquals(viewModel.state.value.yesterday.matchCount, MATCHES_COUNT)
            assertEquals(viewModel.state.value.tomorrow.matchCount, MATCHES_COUNT)
        }

    }

    @Test
    fun testAllMatchesAndLeaguesCorrectStatus() {
        test {
            val viewModel = get<AllLeaguesWithMatchesViewModel>()
            delay(200)

            val favouriteMatches =
                viewModel.state.value.tomorrow.leaguesWithMatchesUIModelListWithFavourite.count {
                    it.matches.any { it.isFavourite }
                }
            assertEquals(favouriteMatches, FAVOURITE_MATCHES_COUNT)
            viewModel.sendIntent(
                AllLeaguesWithMatchesViewModel.Intent.OnAddOrDeleteMatchFromFavouriteClicked(
                    myAddToDbMatchMock
                )
            )
            delay(200)
            val secondFavouriteMatches =
                viewModel.state.value.tomorrow.leaguesWithMatchesUIModelListWithFavourite.count {
                    it.matches.any { it.isFavourite }
                }
            assertEquals(secondFavouriteMatches, FAVOURITE_MATCHES_COUNT_AFTER_ADDING)
            viewModel.sendIntent(
                AllLeaguesWithMatchesViewModel.Intent.OnAddOrDeleteMatchFromFavouriteClicked(
                    myDeleteMatchToDbMock
                )
            )
            delay(200)
            val thirdFavouriteMatches =
                viewModel.state.value.today.leaguesWithMatchesUIModelListWithFavourite.count {
                    it.matches.any { it.isFavourite }
                }
            assertEquals(thirdFavouriteMatches, FAVOURITE_MATCHES_COUNT)
        }
    }

    @Test
    fun testAddMatchToFavouriteInAllLeaguesScreen() {
        test {
            val viewModel = get<AllLeaguesWithMatchesViewModel>()
            delay(200)
            assertEquals(
                viewModel.state.value.tomorrow.favouriteMatchesList.size,
                FAVOURITE_MATCHES_COUNT
            )
            viewModel.sendIntent(
                AllLeaguesWithMatchesViewModel.Intent.OnAddOrDeleteMatchFromFavouriteClicked(
                    myAddToDbMatchMock
                )
            )
            delay(200)
            assertEquals(
                viewModel.state.value.tomorrow.favouriteMatchesList.size,
                FAVOURITE_MATCHES_COUNT_AFTER_ADDING
            )
        }
    }

    @Test
    fun testDeleteMatchToFavouriteInAllLeaguesScreen() {
        test {
            val viewModel = get<AllLeaguesWithMatchesViewModel>()
            delay(200)
            assertEquals(
                viewModel.state.value.tomorrow.favouriteMatchesList.size,
                FAVOURITE_MATCHES_COUNT
            )
            viewModel.sendIntent(
                AllLeaguesWithMatchesViewModel.Intent.OnAddOrDeleteMatchFromFavouriteClicked(
                    myDeleteMatchToDbMock
                )
            )
            delay(200)
            assertEquals(
                viewModel.state.value.tomorrow.favouriteMatchesList.size,
                FAVOURITE_MATCHES_COUNT_AFTER_DELETING
            )

        }
    }

    companion object {
        val myAddToDbMatchMock = MatchEntity(
            awayTeamMatchInfoEntity = TeamMatchInfoEntity(
                imageUrl = "awayTeamUrl 6",
                name = "AwayTeamName 6",
                id = "6",
                goals = 6,
            ),
            homeTeamMatchInfoEntity = TeamMatchInfoEntity(
                imageUrl = "homeTeamUrl 6",
                name = "HomeTeamName 6",
                id = "6",
                goals = 6,
            ),
            matchId = "6",
            leagueInfo = LeagueEntity(
                leagueName = "LeagueName 6",
                leagueId = "LeagueId 6",
                leagueImageUrl = "leagueImageUrl 6",
            ),
            startTime = Calendar.getInstance().time,
            status = MatchStatusEntity.STARTED,
            isFavourite = false
        )

        val myDeleteMatchToDbMock = MatchEntity(
            awayTeamMatchInfoEntity = TeamMatchInfoEntity(
                imageUrl = "awayTeamUrl 0",
                name = "AwayTeamName 0",
                id = "0",
                goals = 0,
            ),
            homeTeamMatchInfoEntity = TeamMatchInfoEntity(
                imageUrl = "homeTeamUrl 0",
                name = "HomeTeamName 0",
                id = "0",
                goals = 0,
            ),
            matchId = "0",
            leagueInfo = LeagueEntity(
                leagueName = "LeagueName 0",
                leagueId = "LeagueId 0",
                leagueImageUrl = "leagueImageUrl 0",
            ),
            startTime = Calendar.getInstance().time,
            status = MatchStatusEntity.STARTED,
            isFavourite = false
        )
    }
}