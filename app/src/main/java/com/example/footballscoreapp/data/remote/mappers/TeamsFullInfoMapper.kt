package com.example.footballscoreapp.data.remote.mappers

import com.example.footballscoreapp.data.remote.dto.teamInfo.TeamInfoDto
import com.example.footballscoreapp.domain.entities.teams.GenderEntity
import com.example.footballscoreapp.domain.entities.teams.TeamFullInfoEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun TeamInfoDto.toTeamFullInfoEntity() = runCatching {
    TeamFullInfoEntity(
        arenaName = arenaName!!,
        arenaImageUrl = arenaHashImage.convertHashToUrl(),
        countryName = countryName!!,
        countryImageUrl = countryHashImage.convertHashToUrl(),
        genderEntity = gender!!.convertGenderStringToGenderEntity(),
        foundationDate = foundationDate!!.toDate(),
        tournamentName = tournamentName!!
    )
}
    .getOrElse {
        null
    }

private fun String.convertGenderStringToGenderEntity() = when (this) {
    "M" -> GenderEntity.MALE
    "F" -> GenderEntity.FEMALE
    else -> throw RuntimeException("Такого гендера нет")
}

private fun String.toDate(): Date {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
    return format.parse(this)
}


private fun String?.convertHashToUrl() = "https://images.sportdevs.com/$this.png"