package com.example.footballscoreapp.data.remote


import com.andretietz.retrofit.ResponseCache
import com.example.footballscoreapp.BuildConfig
import com.example.footballscoreapp.data.remote.dto.MatchesByDateAnswerItemDto
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

    companion object {
        private const val DATE = "date"
        private const val HEADER_KEY = "Authorization"
        private const val API_KEY_VALUE = "Bearer ${BuildConfig.API_KEY}"
    }
}