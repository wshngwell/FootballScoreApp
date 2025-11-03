package com.example.footballscoreapp.data.remote.dto.matchAdditionalInfo

import com.google.gson.annotations.SerializedName


data class MatchAdditionalInfoDto(
    @SerializedName("arena_name")
    val arenaName: String?,
    @SerializedName("coaches")
    val coachesDto: CoachesDto?,
    @SerializedName("lineups_id")
    val lineupsId: String?,
    @SerializedName("referee_name")
    val refereeName: String?,
)