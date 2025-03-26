package com.example.footballscoreapp.domain.entities

data class TeamMatchInfo(
    val imageUrl: String,
    val name: String,
    val id: String,
    val goals: Int?,
)
