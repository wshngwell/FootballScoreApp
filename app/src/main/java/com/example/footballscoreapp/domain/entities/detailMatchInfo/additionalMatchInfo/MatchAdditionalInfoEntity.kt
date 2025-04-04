package com.example.footballscoreapp.domain.entities.detailMatchInfo.additionalMatchInfo

data class MatchAdditionalInfoEntity(
    val arenaName: String?,
    val homeCoach: CoachEntity?,
    val awayCoach: CoachEntity?,
    val lineupsId: String?,
    val refereeName: String?,
)
