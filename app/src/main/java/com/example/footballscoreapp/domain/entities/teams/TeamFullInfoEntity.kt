package com.example.footballscoreapp.domain.entities.teams

import java.util.Date

data class TeamFullInfoEntity(
    val arenaName: String,
    val arenaImageUrl: String,
    val countryImageUrl: String,
    val countryName: String,
    val foundationDate: Date,
    val genderEntity: GenderEntity,
    val tournamentName: String,
)
