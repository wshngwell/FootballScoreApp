package com.example.footballscoreapp.domain.entities.detailMatchInfo.lineup

data class FootballPlayerEntity(
    val playerId: String,
    val position: String,
    val playerName: String,
    val playerNumber: Int,
    val substitute: Boolean,
    val playerImageUrl: String,
)
