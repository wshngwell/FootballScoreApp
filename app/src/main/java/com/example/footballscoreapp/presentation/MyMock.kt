package com.example.footballscoreapp.presentation

import com.example.footballscoreapp.domain.entities.detailMatchInfo.MatchDetailInfoEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.additionalMatchInfo.CoachEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.additionalMatchInfo.MatchAdditionalInfoEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.matchStatistics.TeamStatisticsEntity
import com.example.footballscoreapp.domain.entities.matches.LeagueEntity
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.domain.entities.matches.MatchStatusEntity
import com.example.footballscoreapp.domain.entities.matches.TeamMatchInfoEntity
import com.example.footballscoreapp.domain.entities.teams.GenderEntity
import com.example.footballscoreapp.domain.entities.teams.TeamFullInfoEntity
import com.example.footballscoreapp.domain.entities.teams.TeamMainInfoEntity
import com.example.footballscoreapp.presentation.allMatchesScreen.LeaguesWithMatchesUIModel
import java.util.Calendar

val myLeagueEntityMock = LeagueEntity(
    "",
    "Belarussian League",
    "https://images.sportdevs.com/\$3afda7f23cc22a5c0a34309debe0e826fec99499a02306983e9793c91e74c4da.png"
)
val myMatchEntityMock = MatchEntity(
    matchId = "234",
    leagueInfo = myLeagueEntityMock,
    startTime = Calendar.getInstance().time,
    awayTeamMatchInfoEntity = TeamMatchInfoEntity(
        teamMainInfoEntity = TeamMainInfoEntity(
            imageUrl = "https://images.sportdevs.com/$3afda7f23cc22a5c0a34309debe0e826fec99499a02306983e9793c91e74c4da.png",
            name = "Fc Barcelonssdgsfdhgfffffffffffffffffffffffffffffffffffffffffffffff",
            id = "1",
        ),
        goals = 3
    ),
    homeTeamMatchInfoEntity = TeamMatchInfoEntity(
        teamMainInfoEntity = TeamMainInfoEntity(
            imageUrl = "https://images.sportdevs.com/fefb927e249eb8807484d80f84a8b6a5df16bcf000855eab3bdbf2492ec3f4c6.png",
            name = "Fc Real Madffffffffffffffffffffffffffffffffffffffffffffffffrid",
            id = "2",
        ),

        goals = 2
    ),
    status = MatchStatusEntity.STARTED,
    isFavourite = false
)
val myLeaguesWithMatchesUIModelMock = LeaguesWithMatchesUIModel(
    league = myLeagueEntityMock,
    matches = listOf(myMatchEntityMock)
)
val mockMatchAdditionalInfo: MatchAdditionalInfoEntity = MatchAdditionalInfoEntity(
    arenaName = "Центральный Гомель",
    homeCoach = CoachEntity(
        coachImageUrl = "",
        coachId = "1",
        coachName = "Andrey"
    ),
    awayCoach = CoachEntity(
        coachImageUrl = "",
        coachId = "2",
        coachName = "Ivan"
    ),
    lineupsId = "1",
    refereeName = "Ivanov"
)


val mockDetailInfoEntity = MatchDetailInfoEntity(
    matchAdditionalInfoEntity = mockMatchAdditionalInfo,
    lineUpEntity = null,
    teamStatisticsEntity = listOf(
        TeamStatisticsEntity(
            awayTeamResult = "1",
            homeTeamResult = "2",
            period = "2",
            type = "Владение мячом"
        )
    )
)

val mockTeamMainInfoEntity = TeamMainInfoEntity(
    imageUrl = "jhewjgjiwegwihegjwbjegjwejig[",
    name = "FC GOMEL",
    id = "12345"
)
val mockTeamFullInfoEntity = TeamFullInfoEntity(
    arenaName = "Tsentralny",
    arenaImageUrl = "kadgjnsdgnjsdgsdg",
    countryImageUrl = "admgknsdgnjsdgl",
    countryName = "Belarus",
    foundationDate = Calendar.getInstance().time,
    genderEntity = GenderEntity.MALE,
    tournamentName = "Vyshaya liga"
)
