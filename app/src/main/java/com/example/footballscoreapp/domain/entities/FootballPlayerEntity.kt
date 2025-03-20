package com.example.footballscoreapp.domain.entities

data class FootballPlayerEntity(
    val playerId: String,
    val position: String,
    val playerName: String,
    val playerNumber: Int,
    val playerImageUrl: String,
)
