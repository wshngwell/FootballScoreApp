package com.example.footballscoreapp.data.remote.dto.matchMainInfo


import com.google.gson.annotations.SerializedName

data class MatchesByDateAnswerItemDto(
    @SerializedName("matches")
    val matches: List<MatchByDateItemDto>
)