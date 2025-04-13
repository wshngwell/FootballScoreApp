package com.example.footballscoreapp.data.remote.mappers

import com.example.footballscoreapp.data.remote.dto.matchMainInfo.MatchByDateItemDto
import com.example.footballscoreapp.domain.entities.matches.LeagueEntity
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.domain.entities.matches.MatchStatusEntity
import com.example.footballscoreapp.domain.entities.matches.TeamMatchInfoEntity
import com.example.footballscoreapp.utils.myLog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun MatchByDateItemDto.mapToMatchEntity() = runCatching {
    MatchEntity(
        matchId = id!!,
        leagueInfo = LeagueEntity(
            leagueId = leagueId!!,
            leagueName = leagueName!!,
            leagueImageUrl = leagueHashImage!!.convertHashToUrl()
        ),
        status = status!!.toMatchStatusEntity(),
        awayTeamMatchInfoEntity = TeamMatchInfoEntity(
            name = awayTeamName!!,
            id = awayTeamId!!,
            imageUrl = awayTeamHashImage!!.convertHashToUrl(),
            goals = awayTeamGoals.convertToGoal(status.toMatchStatusEntity())
        ),
        homeTeamMatchInfoEntity = TeamMatchInfoEntity(
            name = homeTeamName!!,
            id = homeTeamId!!,
            imageUrl = homeTeamHashImage!!.convertHashToUrl(),
            goals = homeTeamGoals.convertToGoal(status.toMatchStatusEntity())
        ),
        startTime = startTime!!.toDate(),
        isFavourite = false
    )
}.getOrElse {
    myLog(it.printStackTrace().toString())
    null
}

private fun String?.convertToGoal(statusEntity: MatchStatusEntity) = when (statusEntity) {
    MatchStatusEntity.NOT_STARTED, MatchStatusEntity.POSTPONED, MatchStatusEntity.CANCELLED,
    MatchStatusEntity.INTERRUPTED -> null

    MatchStatusEntity.STARTED, MatchStatusEntity.FINISHED -> {
        this!!.toInt()
    }
}

private fun String.convertHashToUrl() = "https://images.sportdevs.com/$this.png"

private fun String.toDate(): Date {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
    return format.parse(this)
}


private fun String.toMatchStatusEntity() =
    when (this) {
        "upcoming" -> MatchStatusEntity.NOT_STARTED
        "live" -> MatchStatusEntity.STARTED
        "postponed" -> MatchStatusEntity.POSTPONED
        "finished" -> MatchStatusEntity.FINISHED
        "canceled" -> MatchStatusEntity.CANCELLED
        "interrupted" -> MatchStatusEntity.INTERRUPTED
        else -> throw RuntimeException("такого статуса нет")
    }
