package com.example.footballscoreapp.domain.repositories

import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.entities.detailMatchInfo.MatchDetailInfoEntity

interface IDetailsMatchRepository {

    suspend fun loadMatchDetailedInfo(matchId: String): TResult<MatchDetailInfoEntity, LoadingException>
}