package com.example.footballscoreapp.data.remote.dto.matchStatisctics

import com.google.gson.annotations.SerializedName


data class MatchStatisticsDto(
    @SerializedName("away_team")
    val awayTeam: String?,
    @SerializedName("home_team")
    val homeTeam: String?,
    @SerializedName("period")
    val period: String?,
    @SerializedName("type")
    val type: String?,
)