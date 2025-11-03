package com.example.footballscoreapp.domain.entities.matches

import com.example.footballscoreapp.domain.entities.teams.TeamMainInfoEntity

data class TeamMatchInfoEntity(
    val teamMainInfoEntity: TeamMainInfoEntity,
    val goals: Int?,
)
