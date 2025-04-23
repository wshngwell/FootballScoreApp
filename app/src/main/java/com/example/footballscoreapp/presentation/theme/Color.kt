package com.example.footballscoreapp.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.footballscoreapp.presentation.currentTheme

val myBackGround
    get() = if (currentTheme.value.isSystemDark) {
        backGroundDark
    } else {
        backGroundLight
    }

private val firstColorOfLeagueCardBackGroundDark = Color(0xFF441E69)
private val firstColorOfLeagueCardBackGroundLight = Color(0xF05A4A50)
private val secondColorOfLeagueCardBackGroundDark = Color(0xFF800F3F).copy(alpha = 0.3f)
private val secondColorOfLeagueCardBackGroundLight = Color(0xF8C9A0A9).copy(alpha = 0.3f)
private val leagueCardColorBackGroundDark = Brush.linearGradient(
    colors = listOf(
        firstColorOfLeagueCardBackGroundDark,
        secondColorOfLeagueCardBackGroundDark
    ),
)
private val leagueCardColorBackGroundLight = Brush.linearGradient(
    colors = listOf(
        firstColorOfLeagueCardBackGroundLight,
        secondColorOfLeagueCardBackGroundLight
    ),
)
val leagueCardColorBackGround
    get() = if (currentTheme.value.isSystemDark) {
        leagueCardColorBackGroundDark
    } else {
        leagueCardColorBackGroundLight
    }

val tabRowColorSelected
    get() = if (currentTheme.value.isSystemDark) {
        firstColorOfLeagueCardBackGroundDark
    } else {
        firstColorOfLeagueCardBackGroundLight
    }
val tabRowColorNotSelected
    get() = if (currentTheme.value.isSystemDark) {
        secondColorOfLeagueCardBackGroundDark
    } else {
        secondColorOfLeagueCardBackGroundLight
    }

private val firstColorOfMatchCardBackgroundDark = Color(0xF4042892)
private val firstColorOfMatchCardBackgroundLight = Color(0xF42D3449)
private val secondColorOfMatchCardBackgroundDark = Color(0xFF6C1ABB)
private val secondColorOfMatchCardBackgroundLight = Color(0xFF41354D)
val matchColorCardBackGroundDark = Brush.linearGradient(
    colors = listOf(secondColorOfMatchCardBackgroundDark, firstColorOfMatchCardBackgroundDark),
)
val matchColorCardBackGroundLight = Brush.linearGradient(
    colors = listOf(firstColorOfMatchCardBackgroundLight, secondColorOfMatchCardBackgroundLight),
)
val matchColorCardBackGround
    get() = if (currentTheme.value.isSystemDark) {
        matchColorCardBackGroundDark
    } else {
        matchColorCardBackGroundLight
    }
val teamMainInfoColor
    get() = if (currentTheme.value.isSystemDark) {
        firstColorOfMatchCardBackgroundDark
    } else {
        firstColorOfMatchCardBackgroundLight
    }
val scrollBarColor
    get() = if (currentTheme.value.isSystemDark) {
        Color(0xFF990F3F).copy(alpha = 0.5f)
    } else {
        firstColorOfLeagueCardBackGroundLight
    }

private val backGroundLight = Color.White
private val backGroundDark = Color(0xFF2E3144)

private val additionalColorBackGroundDark = Color(0xFF364185)
private val additionalColorBackGroundLight = Color(0xFF818492)
val myLeagueInAdditionalMatchInfoBackgroundColor
    get() = if (currentTheme.value.isSystemDark) {
        additionalColorBackGroundDark
    } else {
        additionalColorBackGroundLight
    }
private val myMatchInAdditionalMatchInfoBackgroundColorDark = Color(0xFF37417C)
private val myMatchInAdditionalMatchInfoBackgroundColorLight = Color(0xFF6A6F8A)
val myMatchInAdditionalMatchInfoBackgroundColor
    get() = if (currentTheme.value.isSystemDark) {
        myMatchInAdditionalMatchInfoBackgroundColorDark
    } else {
        myMatchInAdditionalMatchInfoBackgroundColorLight
    }
private val allGamesInfoBackGroundDark = Color(0xFF3F51B5)
private val allGamesInfoBackGroundLight = Color(0xF81A4046)
val allGamesInfoBackGround
    get() = if (currentTheme.value.isSystemDark) {
        allGamesInfoBackGroundDark
    } else {
        allGamesInfoBackGroundLight
    }

val fullTeamCategoryInfoBackGroundColor
    get() = bottomBarBackGroundColor

val pink = Color(0xFFEB588B)
val blue = Color(0xFF2196F3)

val textColor = Color.White

val noLineUpTextColor = Color(0xFF990F3F)
val onLiveScoreContent = Color.Red

val categoriesInDetailsColor
    get() = leagueCardColorBackGround
val onDefaultMatchColorContent = textColor
val onMatchLiveCardColorContent = Color.Red
val onMatchNotLiveCardColorContent = onDefaultMatchColorContent

val bottomBarBackGroundColorDark = Color(0xFF990F3F).copy(alpha = 0.3f)
val bottomBarBackGroundColorLight = Color(0xFF3F3C3D).copy(alpha = 0.3f)
val bottomBarBackGroundColor
    get() = if (currentTheme.value.isSystemDark) {
        bottomBarBackGroundColorDark
    } else {
        bottomBarBackGroundColorLight
    }


val selectedBottomBarColor = Color.White
val unselectedBottomBarColor = Color.White.copy(alpha = 0.5f)

val defaultProgressBarBrush =
    Brush.linearGradient(listOf(firstColorOfLeagueCardBackGroundDark, Color.Red))
val videoBufferedProgressBarBrush =
    Brush.linearGradient(listOf(noLineUpTextColor, Color.Red))
val timeOfPlayerColor = Color.Red
val inactiveTrackPlayerColor = allGamesInfoBackGround
val activeTrackPlayerColor = noLineUpTextColor
val thumbPlayerColor = Color.Yellow
val videoControlsButtonsColor = noLineUpTextColor

val settingOptionCardColorBackground
    get() = matchColorCardBackGround

val themesModeOptionsBackGround
    get() = leagueCardColorBackGround

val themesModeOptionsBackGroundSelected = Brush.linearGradient(
    colors = listOf(
        Color.Black,
        Color.Black,
    ),
)


val themeChangerIconColor = Color.White