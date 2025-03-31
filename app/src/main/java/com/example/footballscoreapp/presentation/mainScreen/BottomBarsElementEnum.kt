package com.example.footballscoreapp.presentation.mainScreen

import com.example.footballscoreapp.R
import com.example.footballscoreapp.presentation.destinations.DirectionDestination
import com.example.footballscoreapp.presentation.destinations.FavouriteMatchesScreenDestination
import com.example.footballscoreapp.presentation.destinations.LeaguesScreenDestination
import com.example.footballscoreapp.presentation.destinations.LiveMatchesScreenDestination

enum class BottomBarsElementEnum(
    val textResources: Int,
    val selectedIconFromResources: Int,
    val unselectedIconFromResources: Int,
    val destination: DirectionDestination,
) {
    AllMatchesScreen(
        textResources = R.string.all_matches,
        selectedIconFromResources = R.drawable.white_all_matches_icon,
        unselectedIconFromResources = R.drawable.black_all_matches_icon,
        destination = LeaguesScreenDestination,
    ),
    LiveMatchesScreen(
        textResources = R.string.live_matches,
        selectedIconFromResources = R.drawable.white_live_matches_icon,
        unselectedIconFromResources = R.drawable.black_live_matches_icon,
        destination = LiveMatchesScreenDestination
    ),
    FavouriteMatchesScreen(
        textResources = R.string.favourite_matches,
        selectedIconFromResources = R.drawable.white_favourite_matches_icon,
        unselectedIconFromResources = R.drawable.black_favourite_matches_icon,
        destination = FavouriteMatchesScreenDestination
    )
}