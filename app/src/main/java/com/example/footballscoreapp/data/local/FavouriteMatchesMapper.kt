package com.example.footballscoreapp.data.local

import com.example.footballscoreapp.data.local.dbModels.LeagueDbModel
import com.example.footballscoreapp.data.local.dbModels.MatchDbModel
import com.example.footballscoreapp.data.local.dbModels.MatchStatusDbModel
import com.example.footballscoreapp.data.local.dbModels.TeamMatchInfoDbModel
import com.example.footballscoreapp.domain.entities.LeagueEntity
import com.example.footballscoreapp.domain.entities.MatchEntity
import com.example.footballscoreapp.domain.entities.MatchStatusEntity
import com.example.footballscoreapp.domain.entities.TeamMatchInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun MatchDbModel.toMatchEntity() = MatchEntity(
    matchId = matchId,
    leagueInfo = LeagueEntity(
        leagueId = leagueInfo.leagueId,
        leagueName = leagueInfo.leagueName,
        leagueImageUrl = leagueInfo.leagueImageUrl
    ),
    status = status.toMatchStatusEntity(),
    awayTeamMatchInfo = TeamMatchInfo(
        name = awayTeamMatchInfo.name,
        id = awayTeamMatchInfo.id,
        imageUrl = awayTeamMatchInfo.imageUrl,
        goals = awayTeamMatchInfo.goals
    ),
    homeTeamMatchInfo = TeamMatchInfo(
        name = homeTeamMatchInfo.name,
        id = homeTeamMatchInfo.id,
        imageUrl = homeTeamMatchInfo.imageUrl,
        goals = homeTeamMatchInfo.goals
    ),
    startTime = startTime.toDate()
)

fun MatchEntity.toMatchDbModel() = MatchDbModel(
    matchId = matchId,
    leagueInfo = LeagueDbModel(
        leagueId = leagueInfo.leagueId,
        leagueName = leagueInfo.leagueName,
        leagueImageUrl = leagueInfo.leagueImageUrl
    ),
    status = status.toMatchStatusDbModel(),
    awayTeamMatchInfo = TeamMatchInfoDbModel(
        name = awayTeamMatchInfo.name,
        id = awayTeamMatchInfo.id,
        imageUrl = awayTeamMatchInfo.imageUrl,
        goals = awayTeamMatchInfo.goals
    ),
    homeTeamMatchInfo = TeamMatchInfoDbModel(
        name = homeTeamMatchInfo.name,
        id = homeTeamMatchInfo.id,
        imageUrl = homeTeamMatchInfo.imageUrl,
        goals = homeTeamMatchInfo.goals
    ),
    startTime = startTime.parseDateToString()
)


fun Date.parseDateToString(): String {
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return outputFormat.format(this)
}

private fun String.toDate(): Date {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.parse(this)
}


fun MatchStatusDbModel.toMatchStatusEntity() = when (this) {
    MatchStatusDbModel.NOT_STARTED -> MatchStatusEntity.NOT_STARTED
    MatchStatusDbModel.STARTED -> MatchStatusEntity.STARTED
    MatchStatusDbModel.FINISHED -> MatchStatusEntity.FINISHED
    MatchStatusDbModel.POSTPONED -> MatchStatusEntity.POSTPONED
}

fun MatchStatusEntity.toMatchStatusDbModel() = when (this) {
    MatchStatusEntity.NOT_STARTED -> MatchStatusDbModel.NOT_STARTED
    MatchStatusEntity.STARTED -> MatchStatusDbModel.STARTED
    MatchStatusEntity.FINISHED -> MatchStatusDbModel.FINISHED
    MatchStatusEntity.POSTPONED -> MatchStatusDbModel.POSTPONED
}