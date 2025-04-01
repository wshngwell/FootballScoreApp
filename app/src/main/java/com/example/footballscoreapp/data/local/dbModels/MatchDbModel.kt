package com.example.footballscoreapp.data.local.dbModels

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MatchDbModel")
data class MatchDbModel(
    @PrimaryKey
    val matchId: String,
    @Embedded
    val leagueInfo: LeagueDbModel,

    val status: MatchStatusDbModel,

    @Embedded(prefix = "away")
    val awayTeamMatchInfo: TeamMatchInfoDbModel,
    @Embedded(prefix = "home")
    val homeTeamMatchInfo: TeamMatchInfoDbModel,
    val startTime: String,
)
