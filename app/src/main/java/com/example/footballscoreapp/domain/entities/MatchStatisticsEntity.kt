package com.example.footballscoreapp.domain.entities

data class MatchStatisticsEntity(
    val matchId: String,
    val homeTeamStatistics: TeamStatisticsEntity,
    val awayTeamStatistics: TeamStatisticsEntity
)
