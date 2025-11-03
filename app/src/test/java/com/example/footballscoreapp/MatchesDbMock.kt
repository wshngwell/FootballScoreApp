package com.example.footballscoreapp

import com.example.footballscoreapp.ApiServiceMock.Companion.FAVOURITE_MATCHES_COUNT
import com.example.footballscoreapp.data.local.MatchesDao
import com.example.footballscoreapp.data.local.dbModels.LeagueDbModel
import com.example.footballscoreapp.data.local.dbModels.MatchDbModel
import com.example.footballscoreapp.data.local.dbModels.MatchStatusDbModel
import com.example.footballscoreapp.data.local.dbModels.TeamMatchInfoDbModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.koin.dsl.module

class MatchesDbMock {

    val module = module {
        single<MatchesDao> {
            val matchesDao = object : MatchesDao {

                val matches = (0 until FAVOURITE_MATCHES_COUNT).map {
                    MatchDbModel(
                        awayTeamMatchInfo = TeamMatchInfoDbModel(
                            imageUrl = "awayTeamUrl $it",
                            name = "AwayTeamName $it",
                            id = "$it",
                            goals = it,
                        ),
                        homeTeamMatchInfo = TeamMatchInfoDbModel(
                            imageUrl = "homeTeamUrl $it",
                            name = "HomeTeamName $it",
                            id = "$it",
                            goals = it,
                        ),
                        matchId = "$it",
                        leagueInfo = LeagueDbModel(
                            leagueName = "LeagueName $it",
                            leagueId = "LeagueId $it",
                            leagueImageUrl = "leagueImageUrl $it",
                        ),
                        startTime = 14326534635,
                        status = MatchStatusDbModel.STARTED,
                    )
                }

                val matchesStateFlow = MutableStateFlow(matches)

                override fun getMatches(): Flow<List<MatchDbModel>> = matchesStateFlow

                override fun addMatchToDb(listOfMatchDbModels: List<MatchDbModel>) {

                    val updatedMatchList = (listOfMatchDbModels + matchesStateFlow.value)
                        .distinctBy { it.matchId }
                    matchesStateFlow.update { updatedMatchList }
                }

                override fun deleteMatchFromDb(listOfMatchId: List<String>) {
                    val newList = matchesStateFlow.value.toMutableList()
                    listOfMatchId.forEach { onDeletingMatches ->
                        newList.removeIf { it.matchId == onDeletingMatches }
                    }
                    matchesStateFlow.update { newList }
                }
            }
            matchesDao
        }
    }
}