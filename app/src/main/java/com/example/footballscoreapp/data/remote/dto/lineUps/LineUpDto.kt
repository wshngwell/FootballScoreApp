package com.example.footballscoreapp.data.remote.dto.lineUps

import com.google.gson.annotations.SerializedName


data class LineUpDto(
    @SerializedName("away_team")
    val awayTeam: TeamDto?,
    @SerializedName("home_team")
    val homeTeamDto: TeamDto?,
    @SerializedName("id")
    val id: String?
)