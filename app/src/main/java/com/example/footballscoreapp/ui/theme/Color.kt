package com.example.footballscoreapp.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.footballscoreapp.currentTheme


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
val myBackGround
    get() = if (currentTheme.value.isSystemDark) {
        Color(0xFF2E3144)
    } else {
        Color.White
    }

val myLeagueInAdditionalMatchInfoBackgroundColor = Color(0xFF3C4269)
val myMatchInAdditionalMatchInfoBackgroundColor = Color(0xFF37417C)
val allGamesInfoBackGround = Color(0xFF3F51B5)
val onLeagueColorContent = Color.White
val noLineUpTextColor = Color(0xFF990F3F)
val onLiveScoreContent = Color.Red

val categoriesInDetailsColor = leagueCardColorBackGround

val onDefaultMatchColorContent = Color.White
val onMatchLiveCardColorContent = Color.Red
val onMatchNotLiveCardColorContent = Color.White

val bottomBarBackGroundColor = Color(0xFF990F3F).copy(alpha = 0.3f)


val selectedBottomBarColor = Color.White
val unselectedBottomBarColor = Color.White.copy(alpha = 0.5f)

val defaultProgressBarBrush =
    Brush.linearGradient(listOf(firstColorOfLeagueCardBackGround, Color.Red))
val videoBufferedProgressBarBrush =
    Brush.linearGradient(listOf(noLineUpTextColor, Color.Red))
val timeOfPlayerColor = Color.Red
val inactiveTrackPlayerColor = allGamesInfoBackGround
val activeTrackPlayerColor = noLineUpTextColor
val thumbPlayerColor = Color.Yellow

val videoControlsButtonsColor = noLineUpTextColor
val settingOptionCardColorBackground = matchColorCardBackGround

val themesModeOptionsBackGround = leagueCardColorBackGround


val themeChangerIconColor = Color.White