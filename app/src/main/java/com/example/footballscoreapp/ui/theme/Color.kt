package com.example.footballscoreapp.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val firstColorOfLeagueCardBackGround = Color(0xFF441E69)
val secondColorOfLeagueCardBackGround = Color(0xFF990F3F).copy(alpha = 0.3f)
val leagueCardColorBackGround = Brush.linearGradient(
    colors = listOf(firstColorOfLeagueCardBackGround, secondColorOfLeagueCardBackGround),
)

val firstColorOfMatchCardBackground = Color(0xF4042892)
val secondColorOfMatchCardBackground = Color(0xFF6C1ABB)
val matchColorCardBackGround = Brush.linearGradient(
    colors = listOf(secondColorOfMatchCardBackground, firstColorOfMatchCardBackground),
)

val scrollBarColor = Color(0xFF990F3F).copy(alpha = 0.5f)
val myBackGround = Color(0xFF2E3144)
val allGamesInfoBackGround = Color(0xFF3F51B5)

val onLeagueColorContent = Color.White

val onDefaultMatchColorContent = Color.White
val onMatchLiveCardColorContent = Color.Red
val onMatchNotLiveCardColorContent = Color.White

val bottomBarBackGroundColor = Color(0xFF990F3F).copy(alpha = 0.3f)
val selectedBottomBarColor = Color.White
val unselectedBottomBarColor = Color.White.copy(alpha = 0.5f)

val myProgressBarColor =
    Brush.linearGradient(listOf(firstColorOfLeagueCardBackGround, Color.Red))