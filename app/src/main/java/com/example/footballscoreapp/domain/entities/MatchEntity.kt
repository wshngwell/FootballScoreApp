package com.example.footballscoreapp.domain.entities

data class MatchEntity(

    val matchId: Int,
    val leagueInfo: LeagueEntity,
    val status: MatchStatusEntity,
    val arenaName: String,
    val homeTeamName: String,
    val homeTeamId: Int,
    val awayTeamName: String,
    val awayTeamId: Int,
    val countryName: String,
    val startTime: String,
    val matchStatisticsEntity: MatchStatisticsEntity?,
    val lineUp: List<FootballPlayerEntity>?

)
