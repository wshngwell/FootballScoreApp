package com.example.footballscoreapp.data.local

import com.example.footballscoreapp.data.local.dbModels.LeagueDbModel
import com.example.footballscoreapp.data.local.dbModels.MatchDbModel
import com.example.footballscoreapp.data.local.dbModels.MatchStatusDbModel
import com.example.footballscoreapp.data.local.dbModels.TeamMatchInfoDbModel
import com.example.footballscoreapp.domain.entities.matches.LeagueEntity
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.domain.entities.matches.MatchStatusEntity
import com.example.footballscoreapp.domain.entities.matches.TeamMatchInfoEntity
import com.example.footballscoreapp.domain.entities.teams.TeamMainInfoEntity
import java.util.Date


fun MatchDbModel.toMatchEntity() = MatchEntity(
    matchId = matchId,
    leagueInfo = LeagueEntity(
        leagueId = leagueInfo.leagueId,
        leagueName = leagueInfo.leagueName,
        leagueImageUrl = leagueInfo.leagueImageUrl
    ),
    status = status.toMatchStatusEntity(),
    awayTeamMatchInfoEntity = TeamMatchInfoEntity(
        teamMainInfoEntity = TeamMainInfoEntity(
            name = awayTeamMatchInfo.name,
            id = awayTeamMatchInfo.id,
            imageUrl = awayTeamMatchInfo.imageUrl,
        ),
        goals = awayTeamMatchInfo.goals
    ),
    homeTeamMatchInfoEntity = TeamMatchInfoEntity(
        teamMainInfoEntity = TeamMainInfoEntity(
            name = homeTeamMatchInfo.name,
            id = homeTeamMatchInfo.id,
            imageUrl = homeTeamMatchInfo.imageUrl,
        ),
        goals = homeTeamMatchInfo.goals
    ),
    startTime = Date(startTime),
    isFavourite = true
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

        name = awayTeamMatchInfoEntity.teamMainInfoEntity.name,
        id = awayTeamMatchInfoEntity.teamMainInfoEntity.id,
        imageUrl = awayTeamMatchInfoEntity.teamMainInfoEntity.imageUrl,
        goals = awayTeamMatchInfoEntity.goals
    ),
    homeTeamMatchInfo = TeamMatchInfoDbModel(
        name = homeTeamMatchInfoEntity.teamMainInfoEntity.name,
        id = homeTeamMatchInfoEntity.teamMainInfoEntity.id,
        imageUrl = homeTeamMatchInfoEntity.teamMainInfoEntity.imageUrl,
        goals = homeTeamMatchInfoEntity.goals
    ),
    startTime = startTime.time
)

fun MatchStatusDbModel.toMatchStatusEntity() = when (this) {
    MatchStatusDbModel.NOT_STARTED -> MatchStatusEntity.NOT_STARTED
    MatchStatusDbModel.STARTED -> MatchStatusEntity.STARTED
    MatchStatusDbModel.FINISHED -> MatchStatusEntity.FINISHED
    MatchStatusDbModel.POSTPONED -> MatchStatusEntity.POSTPONED
    MatchStatusDbModel.CANCELLED -> MatchStatusEntity.CANCELLED
    MatchStatusDbModel.INTERRUPTED -> MatchStatusEntity.INTERRUPTED
}

fun MatchStatusEntity.toMatchStatusDbModel() = when (this) {
    MatchStatusEntity.NOT_STARTED -> MatchStatusDbModel.NOT_STARTED
    MatchStatusEntity.STARTED -> MatchStatusDbModel.STARTED
    MatchStatusEntity.FINISHED -> MatchStatusDbModel.FINISHED
    MatchStatusEntity.POSTPONED -> MatchStatusDbModel.POSTPONED
    MatchStatusEntity.CANCELLED -> MatchStatusDbModel.CANCELLED
    MatchStatusEntity.INTERRUPTED -> MatchStatusDbModel.INTERRUPTED
}