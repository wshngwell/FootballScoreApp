package com.example.footballscoreapp.data.remote


import com.andretietz.retrofit.ResponseCache
import com.example.footballscoreapp.BuildConfig
import com.example.footballscoreapp.data.remote.dto.MatchesByDateAnswerDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("matches-by-date")
    @ResponseCache(1, TimeUnit.MINUTES)
    suspend fun getMatchesByDate(
        @Header(API_KEY) apiKey: String = API_KEY_VALUE,
        @Query(DATE) date: String,
    ): MatchesByDateAnswerDto

    companion object {
        private const val DATE = "date"
        private const val API_KEY = "Authorization"
        private const val API_KEY_VALUE = "Bearer ${BuildConfig.API_KEY}"
    }
}