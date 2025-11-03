package com.example.footballscoreapp.domain.entities.matches

import java.util.Date

data class MatchEntity(
    val matchId: String,
    val leagueInfo: LeagueEntity,
    val status: MatchStatusEntity,
    val awayTeamMatchInfoEntity: TeamMatchInfoEntity,
    val homeTeamMatchInfoEntity: TeamMatchInfoEntity,
    val startTime: Date,
    val isFavourite: Boolean
)
