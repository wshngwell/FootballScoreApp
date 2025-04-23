package com.example.footballscoreapp.presentation.teamDetailsScreen

import com.example.footballscoreapp.domain.entities.teams.GenderEntity
import com.example.footballscoreapp.presentation.theme.blue
import com.example.footballscoreapp.presentation.theme.pink

fun getFullTeamInfoBackGroundColor(genderEntity: GenderEntity) = when (genderEntity) {
    GenderEntity.MALE -> blue
    GenderEntity.FEMALE -> pink
}