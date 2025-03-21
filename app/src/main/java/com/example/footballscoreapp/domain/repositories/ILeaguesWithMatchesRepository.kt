package com.example.footballscoreapp.domain.repositories

import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.MatchEntity
import com.example.footballscoreapp.domain.entities.TResult
import java.util.Date

interface ILeaguesWithMatchesRepository {

    suspend fun loadMatchesForLeague(date: Date): TResult<List<MatchEntity>, LoadingException>
}