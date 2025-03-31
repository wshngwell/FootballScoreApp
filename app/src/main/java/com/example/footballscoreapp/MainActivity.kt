package com.example.footballscoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.footballscoreapp.presentation.NavGraphs
import com.example.footballscoreapp.presentation.mainScreen.BottomBar
import com.example.footballscoreapp.ui.theme.FootballScoreAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.rememberNavHostEngine

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FootballScoreAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .systemBarsPadding(),
                    bottomBar = {
                        BottomBar(
                            navController = navController,
                        )
                    }
                ) { paddingValues ->

                    Column(modifier = Modifier.padding(paddingValues)) {
                        val navHostEngine = rememberNavHostEngine(
                            rootDefaultAnimations = RootNavGraphDefaultAnimations(
                                enterTransition = { fadeIn(animationSpec = tween(500)) },
                                exitTransition = { fadeOut(animationSpec = tween(500)) },
                                popEnterTransition = { fadeIn(animationSpec = tween(500)) },
                                popExitTransition = { fadeOut(animationSpec = tween(500)) }
                            )
                        )

                        DestinationsNavHost(
                            navController = navController,
                            navGraph = NavGraphs.root,
                            engine = navHostEngine
                        )
                    }
                }
            }
        }
    }
}