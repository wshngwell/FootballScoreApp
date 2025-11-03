package com.example.footballscoreapp.presentation.allMatchesScreen

import com.example.footballscoreapp.domain.entities.matches.LeagueEntity
import com.example.footballscoreapp.domain.entities.matches.MatchEntity

data class LeaguesWithMatchesUIModel(
    val league: LeagueEntity,
    val matches: List<MatchEntity>,
    val isExpanded: Boolean = false
)
