package com.example.footballscoreapp.data.remote.mappers

import com.example.footballscoreapp.data.remote.dto.lineUps.LineUpDto
import com.example.footballscoreapp.data.remote.dto.matchAdditionalInfo.MatchAdditionalInfoDto
import com.example.footballscoreapp.data.remote.dto.matchStatisctics.MatchStatisticDtoAnswer
import com.example.footballscoreapp.data.remote.dto.matchStatisctics.MatchStatisticsDto
import com.example.footballscoreapp.domain.entities.detailMatchInfo.additionalMatchInfo.CoachEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.additionalMatchInfo.MatchAdditionalInfoEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.lineup.FootballPlayerEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.lineup.LineUpEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.lineup.TeamLineUpInfoEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.matchStatistics.TeamStatisticsEntity


fun MatchAdditionalInfoDto.toMatchAdditionalInfoEntity() = runCatching {
    MatchAdditionalInfoEntity(
        arenaName = arenaName,
        homeCoach = CoachEntity(
            coachImageUrl = coachesDto?.homeCoachHashImage.convertHashToUrl(),
            coachName = coachesDto?.homeCoachName!!,
            coachId = coachesDto.homeCoachId!!
        ),
        awayCoach = CoachEntity(
            coachImageUrl = coachesDto.awayCoachHashImage.convertHashToUrl(),
            coachName = coachesDto.awayCoachName!!,
            coachId = coachesDto.awayCoachId!!
        ),
        lineupsId = lineupsId,
        refereeName = refereeName
    )
}.getOrElse {
    null
}

fun LineUpDto.toLineUpEntity() = runCatching {
    LineUpEntity(
        lineUpId = id!!,
        awayTeam = TeamLineUpInfoEntity(
            formation = awayTeam!!.formation!!,
            playerColorNumber = awayTeam.playerColorNumber!!,
            players = awayTeam.players!!.map {
                FootballPlayerEntity(
                    playerId = it.playerId!!,
                    position = it.position!!,
                    playerNumber = it.jerseyNumber!!.toInt(),
                    playerName = it.playerName!!,
                    substitute = it.substitute!!,
                    playerImageUrl = it.playerHashImage.convertHashToUrl(),
                )
            }
        ),
        homeTeam = TeamLineUpInfoEntity(
            formation = homeTeamDto!!.formation!!,
            playerColorNumber = homeTeamDto.playerColorNumber!!,
            players = homeTeamDto.players!!.map {
                FootballPlayerEntity(
                    playerId = it.playerId!!,
                    position = it.position!!,
                    playerNumber = it.jerseyNumber!!.toInt(),
                    playerName = it.playerName!!,
                    substitute = it.substitute!!,
                    playerImageUrl = it.playerHashImage.convertHashToUrl(),
                )
            }
        ),
    )
}.getOrElse {
    null
}

fun MatchStatisticsDto.toTeamStatisticsEntity() = runCatching {
    TeamStatisticsEntity(
        awayTeamResult = awayTeam!!,
        homeTeamResult = homeTeam!!,
        period = period!!,
        type = type!!
    )
}.getOrElse {
    null
}

fun MatchStatisticDtoAnswer.toListOfTeamStatisticsEntity() = runCatching {
    matchStatisticsDtos!!
        .filter { it.period == "ALL" }
        .mapNotNull {
            it.toTeamStatisticsEntity()
        }
}.getOrElse {
    null
}

private fun String?.convertHashToUrl() = "https://images.sportdevs.com/$this.png"