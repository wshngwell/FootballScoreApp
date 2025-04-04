package com.example.footballscoreapp.presentation.mathcesUi

import com.example.footballscoreapp.R
import com.example.footballscoreapp.domain.entities.matches.MatchStatusEntity

fun MatchStatusEntity.parseMatchStatusInfoToString() = when (this) {
    MatchStatusEntity.NOT_STARTED -> R.string.match_not_started
    MatchStatusEntity.STARTED -> R.string.match_is_going
    MatchStatusEntity.FINISHED -> R.string.match_finished
    MatchStatusEntity.POSTPONED -> R.string.match_postponed
    MatchStatusEntity.CANCELLED -> R.string.match_cancelled
    MatchStatusEntity.INTERRUPTED -> R.string.match_interrupted
}