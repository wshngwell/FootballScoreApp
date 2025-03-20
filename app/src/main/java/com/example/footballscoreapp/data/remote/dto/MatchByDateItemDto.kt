package com.example.footballscoreapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class MatchByDateItemDto(

    @SerializedName("away_team_hash_image")
    val awayTeamHashImage: String? = null,
    @SerializedName("away_team_id")
    val awayTeamId: String? = null,
    @SerializedName("away_team_name")
    val awayTeamName: String? = null,
    @SerializedName("home_team_hash_image")
    val homeTeamHashImage: String? = null,
    @SerializedName("home_team_id")
    val homeTeamId: String? = null,
    @SerializedName("home_team_name")
    val homeTeamName: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("league_hash_image")
    val leagueHashImage: String? = null,
    @SerializedName("league_id")
    val leagueId: String? = null,
    @SerializedName("league_name")
    val leagueName: String? = null,
    @SerializedName("start_time")
    val startTime: String? = null,
    @SerializedName("status")
    val status: String? = null,
)