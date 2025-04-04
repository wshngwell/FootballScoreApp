package com.example.footballscoreapp.domain.entities.detailMatchInfo

import com.example.footballscoreapp.domain.entities.detailMatchInfo.additionalMatchInfo.MatchAdditionalInfoEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.lineup.LineUpEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.matchStatistics.TeamStatisticsEntity

data class MatchDetailInfoEntity(
    val matchAdditionalInfoEntity: MatchAdditionalInfoEntity?,
    val teamStatisticsEntity: List<TeamStatisticsEntity>?,
    val lineUpEntity: LineUpEntity?,
)
