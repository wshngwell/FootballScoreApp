package com.example.footballscoreapp.domain.entities

import java.util.Date

data class MatchEntity(

    val matchId: String,
    val leagueInfo: LeagueEntity,
    val status: MatchStatusEntity,
    val homeTeamImageUrl: String,
    val awayTeamImageUrl: String,
    val homeTeamName: String,
    val homeTeamId: String,
    val awayTeamName: String,
    val awayTeamId: String,
    val startTime: Date,
)
