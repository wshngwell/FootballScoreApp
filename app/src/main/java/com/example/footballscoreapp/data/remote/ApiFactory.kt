package com.example.footballscoreapp.data.remote

import android.app.Application
import com.example.footballscoreapp.data.remote.OkhttpCache.setOkhttpCache
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://football.sportdevs.com/"

    fun getApiService(application: Application) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(MyOkHttpClient().get())
        .build()
        .setOkhttpCache(application)
        .create(ApiService::class.java)
}