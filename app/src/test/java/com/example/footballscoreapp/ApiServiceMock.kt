package com.example.footballscoreapp

import com.example.footballscoreapp.data.remote.ApiService
import com.example.footballscoreapp.data.remote.dto.lineUps.LineUpDto
import com.example.footballscoreapp.data.remote.dto.lineUps.PlayerDto
import com.example.footballscoreapp.data.remote.dto.lineUps.TeamDto
import com.example.footballscoreapp.data.remote.dto.matchAdditionalInfo.CoachesDto
import com.example.footballscoreapp.data.remote.dto.matchAdditionalInfo.MatchAdditionalInfoDto
import com.example.footballscoreapp.data.remote.dto.matchMainInfo.MatchByDateItemDto
import com.example.footballscoreapp.data.remote.dto.matchMainInfo.MatchesByDateAnswerItemDto
import com.example.footballscoreapp.data.remote.dto.matchStatisctics.MatchStatisticDtoAnswer
import com.example.footballscoreapp.data.remote.dto.matchStatisctics.MatchStatisticsDto
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.Locale

class ApiServiceMock {

    val module = module {

        single<ApiService> {

            val apiService = object : ApiService {

                override suspend fun getMatchesByDate(date: String): List<MatchesByDateAnswerItemDto> {
                    val formatter = SimpleDateFormat("'eq.'yyyy-MM-dd", Locale.getDefault())

                    runCatching {
                        formatter.parse(date)

                        return listOf(
                            MatchesByDateAnswerItemDto(
                                matches = (0 until MATCHES_COUNT).map {
                                    MatchByDateItemDto(
                                        awayTeamHashImage = "awayTeamHashImage $it",
                                        awayTeamId = "$it",
                                        awayTeamName = "AwayTeamName $it",
                                        homeTeamHashImage = "homeTeamHashImage $it",
                                        homeTeamId = "HomeTeam $it",
                                        homeTeamName = "HomeTeamName $it",
                                        id = "$it",
                                        leagueHashImage = "leagueHashImage $it",
                                        leagueId = "LeagueId $it",
                                        leagueName = "LeagueName $it",
                                        startTime = "2025-04-13T13:00:00\u002B00:00",
                                        status = if (it % 2 == 0) "live" else "finished",
                                        awayTeamGoals = "$it",
                                        homeTeamGoals = "$it"
                                    )
                                }
                            )

                        )

                    }.getOrElse {
                        throw RuntimeException("Incorrect query(date) format")
                    }
                }

                override suspend fun getMatchStatistics(matchId: String): List<MatchStatisticDtoAnswer> {
                    return listOf(
                        MatchStatisticDtoAnswer(
                            matchStatisticsDtos = (0..15).map {
                                MatchStatisticsDto(
                                    awayTeam = "AwayTeam $it",
                                    homeTeam = "HomeTeam $it",
                                    period = if (it == 5 || it == 6) "2ND" else "ALL",
                                    type = "type $it"
                                )
                            }
                        )
                    )
                }

                override suspend fun getMatchAdditionalInfo(matchId: String): List<MatchAdditionalInfoDto> {
                    return listOf(
                        MatchAdditionalInfoDto(
                            arenaName = "ArenaName",
                            coachesDto = CoachesDto(
                                awayCoachHashImage = "f",
                                awayCoachName = "coach",
                                homeCoachHashImage = "a",
                                homeCoachName = "refs",
                                awayCoachId = "1254",
                                homeCoachId = "424"
                            ),
                            lineupsId = LINE_UP_LOADED_ID,
                            refereeName = "sgsf"
                        )
                    )
                }

                override suspend fun getLineUp(lineUpId: String): List<LineUpDto> {
                    return listOf(
                        LineUpDto(
                            awayTeam = TeamDto(
                                formation = "4-3-3",
                                playerColorNumber = "1322",
                                players = (0 until LINE_UP_PLAYERS_SIZE).map {
                                    PlayerDto(
                                        jerseyNumber = "$it",
                                        playerHashImage = "PlayerImage",
                                        playerId = "$it",
                                        playerName = "Player $it",
                                        position = "D",
                                        substitute = false
                                    )
                                }
                            ),
                            homeTeamDto = TeamDto(formation = "4-3-3",
                                playerColorNumber = "1322",
                                players = (0..20).map {
                                    PlayerDto(
                                        jerseyNumber = "$it",
                                        playerHashImage = "PlayerImage",
                                        playerId = "$it",
                                        playerName = "Player $it",
                                        position = "D",
                                        substitute = false
                                    )
                                }),
                            id = "136"
                        )
                    )
                }
            }
            apiService
        }
    }

    companion object {
        const val MATCHES_COUNT = 11
        const val LIVE_MATCHES_COUNT = 6
        const val LIVE_MATCHES_FAVOURITE_COUNT = 3
        const val LIVE_MATCHES_FAVOURITE_COUNT_AFTER_ADDING = LIVE_MATCHES_FAVOURITE_COUNT + 1
        const val LIVE_MATCHES_FAVOURITE_COUNT_AFTER_DELETING =
            LIVE_MATCHES_FAVOURITE_COUNT
        const val FAVOURITE_MATCHES_COUNT = 5
        const val FAVOURITE_MATCHES_COUNT_AFTER_ADDING = FAVOURITE_MATCHES_COUNT + 1
        const val FAVOURITE_MATCHES_COUNT_AFTER_DELETING = FAVOURITE_MATCHES_COUNT-1
        const val LINE_UP_LOADED_ID = "444"
        const val LINE_UP_PLAYERS_SIZE = 21
        const val STATISTICS_SIZE = 14

    }
}