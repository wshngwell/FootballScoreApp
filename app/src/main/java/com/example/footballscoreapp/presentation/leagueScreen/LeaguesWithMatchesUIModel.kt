package com.example.footballscoreapp.presentation.leagueScreen

import com.example.footballscoreapp.domain.entities.LeagueEntity
import com.example.footballscoreapp.domain.entities.MatchEntity

data class LeaguesWithMatchesUIModel(
    val league: LeagueEntity,
    val matches: List<MatchEntity>,
    val isExpanded: Boolean = false
)
