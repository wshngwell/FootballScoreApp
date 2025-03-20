package com.example.footballscoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.footballscoreapp.data.remote.ApiFactory
import com.example.footballscoreapp.data.remote.parseToLoadingException
import com.example.footballscoreapp.data.remote.repositories.LeaguesWithMatchesRepositoryImpl
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.ui.theme.FootballScoreAppTheme
import com.example.footballscoreapp.utils.myLog
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* MainScope().launch {
             val leagues = LeaguesWithMatchesRepositoryImpl(ApiFactory.getApiService(application))
                 .loadMatchesForLeague("eq.2025-03-19")
             when (leagues) {
                 is TResult.Error -> {}
                 is TResult.Success -> {
                     myLog(leagues.data.size.toString())
                 }
             }
         }*/
        enableEdgeToEdge()
        setContent {
            FootballScoreAppTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }*/
            }
        }
    }
}