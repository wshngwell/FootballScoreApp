package com.example.footballscoreapp.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val firstColorOfLeagueCard = Color(0xFF441E69)
val secondColorOfLeagueCard = Color.White.copy(0.5f)
val leagueCardColor = Brush.linearGradient(
    colors = listOf(firstColorOfLeagueCard, secondColorOfLeagueCard),
)
val myBackGround = Color(0xFF2E3144)
val allGamesInfoBackGround = Color(0xFF3F51B5)
val onLeagueColorContent = Color.White
val myProgressBarColor =
    Brush.linearGradient(listOf(firstColorOfLeagueCard, Color.Red))