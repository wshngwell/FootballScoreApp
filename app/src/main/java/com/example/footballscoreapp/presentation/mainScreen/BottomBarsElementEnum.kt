package com.example.footballscoreapp.presentation.mainScreen

import com.example.footballscoreapp.R
import com.example.footballscoreapp.presentation.destinations.DirectionDestination
import com.example.footballscoreapp.presentation.destinations.FavouriteMatchesScreenDestination
import com.example.footballscoreapp.presentation.destinations.LeaguesScreenDestination
import com.example.footballscoreapp.presentation.destinations.LiveMatchesScreenDestination
import com.example.footballscoreapp.presentation.destinations.SettingsScreenDestination

enum class BottomBarsElementEnum(
    val textResources: Int,
    val iconFromResources: Int,
    val destination: DirectionDestination,
) {
    AllMatchesScreen(
        textResources = R.string.all_matches,
        iconFromResources = R.drawable.white_all_matches_icon,
        destination = LeaguesScreenDestination,
    ),
    LiveMatchesScreen(
        textResources = R.string.live_matches,
        iconFromResources = R.drawable.white_live_matches_icon,
        destination = LiveMatchesScreenDestination
    ),
    FavouriteMatchesScreen(
        textResources = R.string.favourite_matches,
        iconFromResources = R.drawable.white_favourite_matches_icon,
        destination = FavouriteMatchesScreenDestination
    ),

    SettingsScreen(
        textResources = R.string.settings,
        iconFromResources = R.drawable.black_settings_icon,
        destination = SettingsScreenDestination
    )
}