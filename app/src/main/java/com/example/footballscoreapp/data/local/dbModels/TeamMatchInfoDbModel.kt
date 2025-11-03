package com.example.footballscoreapp.data.local.dbModels

data class TeamMatchInfoDbModel(
    val imageUrl: String,
    val name: String,
    val id: String,
    val goals: Int?,
)
