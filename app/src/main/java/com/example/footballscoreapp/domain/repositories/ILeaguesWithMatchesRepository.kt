package com.example.footballscoreapp.domain.repositories

import com.example.footballscoreapp.domain.entities.LeagueEntity
import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.MatchEntity
import com.example.footballscoreapp.domain.entities.TResult

interface ILeaguesWithMatchesRepository {

    suspend fun loadLeagues(date: String): TResult<List<LeagueEntity>, LoadingException>

    suspend fun loadMatchesForLeague(date: String): TResult<List<MatchEntity>, LoadingException>
}