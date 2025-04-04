package com.example.footballscoreapp.presentation.leagueScreen

import com.example.footballscoreapp.domain.entities.matches.LeagueEntity
import com.example.footballscoreapp.domain.entities.matches.MatchEntity

data class LeaguesWithMatchesUIModel(
    val league: LeagueEntity,
    val matches: List<MatchEntity>,
    val isExpanded: Boolean = false
)
