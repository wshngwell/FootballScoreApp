package com.example.footballscoreapp.data.remote.dto.matchAdditionalInfo

import com.google.gson.annotations.SerializedName


data class CoachesDto(
    @SerializedName("away_coach_hash_image")
    val awayCoachHashImage: String?,
    @SerializedName("away_coach_id")
    val awayCoachId: String?,
    @SerializedName("away_coach_name")
    val awayCoachName: String?,
    @SerializedName("home_coach_hash_image")
    val homeCoachHashImage: String?,
    @SerializedName("home_coach_id")
    val homeCoachId: String?,
    @SerializedName("home_coach_name")
    val homeCoachName: String?
)