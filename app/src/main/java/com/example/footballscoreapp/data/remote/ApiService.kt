package com.example.footballscoreapp.data.remote


import com.andretietz.retrofit.ResponseCache
import com.example.footballscoreapp.BuildConfig
import com.example.footballscoreapp.data.remote.dto.lineUps.LineUpDto
import com.example.footballscoreapp.data.remote.dto.matchAdditionalInfo.MatchAdditionalInfoDto
import com.example.footballscoreapp.data.remote.dto.matchMainInfo.MatchesByDateAnswerItemDto
import com.example.footballscoreapp.data.remote.dto.matchStatisctics.MatchStatisticDtoAnswer
import com.example.footballscoreapp.data.remote.dto.teamInfo.TeamInfoDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("matches-by-date")
    @ResponseCache(1, TimeUnit.MINUTES)
    @Headers("$HEADER_KEY:$API_KEY_VALUE")
    suspend fun getMatchesByDate(
        @Query(DATE) date: String,
    ): List<MatchesByDateAnswerItemDto>

    @GET("matches-statistics")
    @ResponseCache(1, TimeUnit.MINUTES)
    @Headers("$HEADER_KEY:$API_KEY_VALUE")
    suspend fun getMatchStatistics(
        @Query(MATCH_ID) matchId: String,
    ): List<MatchStatisticDtoAnswer>

    @GET("matches")
    @ResponseCache(1, TimeUnit.MINUTES)
    @Headers("$HEADER_KEY:$API_KEY_VALUE")
    suspend fun getMatchAdditionalInfo(
        @Query(ID) matchId: String,
    ): List<MatchAdditionalInfoDto>

    @GET("matches-lineups")
    @ResponseCache(1, TimeUnit.MINUTES)
    @Headers("$HEADER_KEY:$API_KEY_VALUE")
    suspend fun getLineUp(
        @Query(ID) lineUpId: String,
    ): List<LineUpDto>

    @GET("teams")
    @ResponseCache(1, TimeUnit.MINUTES)
    @Headers("$HEADER_KEY:$API_KEY_VALUE")
    suspend fun getTeamInfo(
        @Query(ID) teamsId: String,
    ): List<TeamInfoDto>


    companion object {
        private const val DATE = "date"
        private const val MATCH_ID = "match_id"
        private const val ID = "id"
        private const val HEADER_KEY = "Authorization"
        private const val API_KEY_VALUE = "Bearer ${BuildConfig.API_KEY}"
    }
}