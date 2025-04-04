package com.example.footballscoreapp.data.remote.dto.lineUps

import com.google.gson.annotations.SerializedName


data class PlayerDto(
    @SerializedName("jersey_number")
    val jerseyNumber: String?,
    @SerializedName("player_hash_image")
    val playerHashImage: String?,
    @SerializedName("player_id")
    val playerId: String?,
    @SerializedName("player_name")
    val playerName: String?,
    @SerializedName("position")
    val position: String?,
    @SerializedName("substitute")
    val substitute: Boolean?
)