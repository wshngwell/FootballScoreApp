package com.example.footballscoreapp.domain.entities

import java.util.Date

data class MatchEntity(
    val matchId: String,
    val leagueInfo: LeagueEntity,
    val status: MatchStatusEntity,
    val awayTeamMatchInfo: TeamMatchInfo,
    val homeTeamMatchInfo: TeamMatchInfo,
    val startTime: Date,
    val isFavourite: Boolean
)
