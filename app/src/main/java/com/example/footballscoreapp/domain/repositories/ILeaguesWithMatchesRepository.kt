package com.example.footballscoreapp.domain.repositories

import com.example.footballscoreapp.domain.entities.LeagueEntity
import com.example.footballscoreapp.domain.entities.MatchEntity

interface ILeaguesWithMatchesRepository {

    suspend fun loadLeagues(): List<LeagueEntity>

    suspend fun loadMatchesForLeague(): List<MatchEntity>
}