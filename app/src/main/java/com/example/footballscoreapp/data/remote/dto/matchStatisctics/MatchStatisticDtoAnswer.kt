package com.example.footballscoreapp.data.remote.dto.matchStatisctics

import com.google.gson.annotations.SerializedName


data class MatchStatisticDtoAnswer(
    @SerializedName("statistics")
    val matchStatisticsDtos: List<MatchStatisticsDto>?
)