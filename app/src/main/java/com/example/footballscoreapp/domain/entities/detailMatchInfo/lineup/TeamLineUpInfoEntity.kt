package com.example.footballscoreapp.domain.entities.detailMatchInfo.lineup

data class TeamLineUpInfoEntity(
    val formation: String,
    val playerColorNumber: String,
    val players: List<FootballPlayerEntity>
)
