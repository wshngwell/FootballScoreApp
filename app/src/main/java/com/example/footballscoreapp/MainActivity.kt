package com.example.footballscoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.footballscoreapp.ui.theme.FootballScoreAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FootballScoreAppTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }*/
            }
        }
    }
}