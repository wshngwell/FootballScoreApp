package com.example.footballscoreapp.domain.entities

data class MatchStatisticsEntity(
    val homeTeamsGoals: Int,
    val awayTeamsGoals: Int,
    val awayTeamAccuratePasses:Int,
    val awayTeamBallPossession:Int,
    val awayTeamShotsOnTarget:Int,
    val awayTeamCornerKicks:Int,
    val awayTeamOffsides:Int,
    val awayTeamPasses:Int,
    val awayTotalShots:Int,
    val homeTeamAccuratePasses:Int,
    val homeTeamBallPossession:Int,
    val homeTeamShotsOnTarget:Int,
    val homeTeamCornerKicks:Int,
    val homeTeamOffsides:Int,
    val homeTeamPasses:Int,
    val homeTotalShots:Int,
)
