package com.example.footballscoreapp.domain.entities.matches

data class TeamMatchInfoEntity(
    val imageUrl: String,
    val name: String,
    val id: String,
    val goals: Int?,
)
