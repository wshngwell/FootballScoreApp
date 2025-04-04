package com.example.footballscoreapp.domain.entities.matches

data class TeamMatchInfo(
    val imageUrl: String,
    val name: String,
    val id: String,
    val goals: Int?,
)
