package com.example.footballscoreapp.domain.entities

data class TeamStatisticsEntity(
    val teamsGoals: Int,
    val teamAccuratePasses: Float,
    val teamShotsOnTarget: Int,
    val teamCornerKicks: Int,
    val teamOffsides: Int,
    val teamPasses: Int,
    val teamsTotalShots: Int,
)
