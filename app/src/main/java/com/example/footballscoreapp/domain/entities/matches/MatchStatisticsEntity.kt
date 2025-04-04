package com.example.footballscoreapp.domain.entities.matches

import com.example.footballscoreapp.domain.entities.detailMatchInfo.matchStatistics.TeamStatisticsEntity

data class MatchStatisticsEntity(
    val matchId: String,
    val homeTeamStatistics: TeamStatisticsEntity,
    val awayTeamStatistics: TeamStatisticsEntity
)
