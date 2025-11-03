package com.example.footballscoreapp.data.remote.dto.teamInfo

import com.google.gson.annotations.SerializedName


data class TeamInfoDto(
    @SerializedName("arena_hash_image")
    val arenaHashImage: String?,
    @SerializedName("arena_name")
    val arenaName: String?,
    @SerializedName("country_hash_image")
    val countryHashImage: String?,
    @SerializedName("country_name")
    val countryName: String?,
    @SerializedName("foundation_date")
    val foundationDate: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("tournament_name")
    val tournamentName: String?,
)