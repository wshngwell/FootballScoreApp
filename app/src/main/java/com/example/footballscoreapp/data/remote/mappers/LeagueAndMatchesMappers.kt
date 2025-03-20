package com.example.footballscoreapp.data.remote.mappers

import com.example.footballscoreapp.data.remote.dto.MatchByDateItemDto
import com.example.footballscoreapp.domain.entities.LeagueEntity
import com.example.footballscoreapp.domain.entities.MatchEntity
import com.example.footballscoreapp.domain.entities.MatchStatusEntity
import com.example.footballscoreapp.utils.myLog
import java.text.SimpleDateFormat
import java.util.Date

fun MatchByDateItemDto.mapToMatchEntity() = runCatching {
    MatchEntity(
        matchId = id!!,
        leagueInfo = LeagueEntity(
            leagueId = leagueId!!,
            leagueName = leagueName!!,
            leagueImageUrl = leagueHashImage!!.convertHashToUrl()
        ),
        status = status!!.toMatchStatusEntity(),
        homeTeamName = homeTeamName!!,
        homeTeamId = homeTeamId!!,
        homeTeamImageUrl = homeTeamHashImage!!.convertHashToUrl(),
        startTime = startTime!!.toDate(),
        awayTeamName = awayTeamName!!,
        awayTeamId = awayTeamId!!,
        awayTeamImageUrl = awayTeamHashImage!!.convertHashToUrl(),
    )
}.getOrElse {
    myLog(it.printStackTrace().toString())
    null
}

fun MatchByDateItemDto.mapToLeagueEntity() = runCatching {
    LeagueEntity(
        leagueId = leagueId!!,
        leagueName = leagueName!!,
        leagueImageUrl = leagueHashImage!!.convertHashToUrl()
    )
}.getOrElse {
    null
}

private fun String.convertHashToUrl() = "https://images.sportdevs.com/$this.png"


private fun String.toDate(): Date {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
    return format.parse(this)
}


private fun String.toMatchStatusEntity() =
    if (this == "upcoming") {
        MatchStatusEntity.NOT_STARTED
    } else if (this == "live") {
        MatchStatusEntity.STARTED
    } else if (this == "postponed") {
        MatchStatusEntity.POSTPONED
    } else {
        MatchStatusEntity.FINISHED
    }