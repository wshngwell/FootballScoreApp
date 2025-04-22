package com.example.footballscoreapp.domain.repositories

import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.entities.teams.TeamFullInfoEntity

interface ITeamsFullInfoRepository {

    suspend fun getTeamFullInfo(teamId: String): TResult<TeamFullInfoEntity, LoadingException>
}