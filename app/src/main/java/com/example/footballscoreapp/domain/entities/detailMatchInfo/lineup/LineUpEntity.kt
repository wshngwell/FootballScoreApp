package com.example.footballscoreapp.domain.entities.detailMatchInfo.lineup

data class LineUpEntity(
    val lineUpId: String,
    val awayTeam: TeamLineUpInfoEntity,
    val homeTeam: TeamLineUpInfoEntity,
)
