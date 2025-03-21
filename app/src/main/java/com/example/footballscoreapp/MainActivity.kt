package com.example.footballscoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.footballscoreapp.ui.theme.FootballScoreAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*MainScope().launch {
            val leagues = LeaguesWithMatchesRepositoryImpl(ApiFactory.getApiService(application))
                .loadMatchesForLeague(Date())
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