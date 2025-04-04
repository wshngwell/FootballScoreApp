package com.example.footballscoreapp.data.remote.dto.lineUps

import com.google.gson.annotations.SerializedName


data class TeamDto(
    @SerializedName("formation")
    val formation: String?,
    @SerializedName("player_color_number")
    val playerColorNumber: String?,
    @SerializedName("players")
    val players: List<PlayerDto>?
)