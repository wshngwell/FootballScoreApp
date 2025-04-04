package com.example.footballscoreapp.presentation

import com.example.footballscoreapp.domain.entities.detailMatchInfo.MatchDetailInfoEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.additionalMatchInfo.CoachEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.additionalMatchInfo.MatchAdditionalInfoEntity
import com.example.footballscoreapp.domain.entities.matches.LeagueEntity
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.domain.entities.matches.MatchStatusEntity
import com.example.footballscoreapp.domain.entities.matches.TeamMatchInfo
import com.example.footballscoreapp.presentation.leagueScreen.LeaguesWithMatchesUIModel
import java.util.Calendar

val myLeagueEntityMock = LeagueEntity("", "", "")
val myMatchEntityMock = MatchEntity(
    matchId = "234",
    leagueInfo = myLeagueEntityMock,
    startTime = Calendar.getInstance().time,
    awayTeamMatchInfo = TeamMatchInfo(
        imageUrl = "https://images.sportdevs.com/$3afda7f23cc22a5c0a34309debe0e826fec99499a02306983e9793c91e74c4da.png",
        name = "Fc Barcelonssdgsfdhgfffffffffffffffffffffffffffffffffffffffffffffff",
        id = "1",
        goals = 3
    ),
    homeTeamMatchInfo = TeamMatchInfo(
        imageUrl = "https://images.sportdevs.com/fefb927e249eb8807484d80f84a8b6a5df16bcf000855eab3bdbf2492ec3f4c6.png",
        name = "Fc Real Madffffffffffffffffffffffffffffffffffffffffffffffffrid",
        id = "2",
        goals = 2
    ),
    status = MatchStatusEntity.STARTED,
    isFavourite = false
)
val myLeaguesWithMatchesUIModelMock = LeaguesWithMatchesUIModel(
    league = myLeagueEntityMock,
    matches = listOf(myMatchEntityMock)
)

val mockDetailInfoEntity = MatchDetailInfoEntity(
    matchAdditionalInfoEntity = MatchAdditionalInfoEntity(
        arenaName = "",
        homeCoach = CoachEntity(
            coachHashImage = "",
            coachId = "",
            coachName = ""
        ),
        awayCoach = CoachEntity(
            coachHashImage = "",
            coachId = "",
            coachName = ""
        ),
        lineupsId = "",
        refereeName = "",
    ),
    lineUpEntity = null,
    teamStatisticsEntity = null
)