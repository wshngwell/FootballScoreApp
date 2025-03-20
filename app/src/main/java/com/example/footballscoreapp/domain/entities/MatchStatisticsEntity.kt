package com.example.footballscoreapp.domain.entities

data class MatchStatisticsEntity(
    val homeTeamStatistics: TeamStatisticsEntity,
    val awayTeamStatistics: TeamStatisticsEntity
)
