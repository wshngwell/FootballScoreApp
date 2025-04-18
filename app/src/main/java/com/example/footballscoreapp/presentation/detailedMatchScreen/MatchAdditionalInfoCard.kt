package com.example.footballscoreapp.presentation.detailedMatchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.footballscoreapp.R
import com.example.footballscoreapp.domain.entities.detailMatchInfo.MatchDetailInfoEntity
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.domain.entities.matches.MatchStatusEntity
import com.example.footballscoreapp.presentation.AllMatchesScreen.LeagueNameWithImageCard
import com.example.footballscoreapp.presentation.mockDetailInfoEntity
import com.example.footballscoreapp.presentation.myMatchEntityMock
import com.example.footballscoreapp.presentation.parseDateToStringFullDate
import com.example.footballscoreapp.ui.theme.categoriesInDetailsColor
import com.example.footballscoreapp.ui.theme.detailTeamImage
import com.example.footballscoreapp.ui.theme.goalsFontSizeInDetails
import com.example.footballscoreapp.ui.theme.imagePadding
import com.example.footballscoreapp.ui.theme.myMatchInAdditionalMatchInfoBackgroundColor
import com.example.footballscoreapp.ui.theme.onLiveScoreContent
import com.example.footballscoreapp.ui.theme.paddingLeagueCardInfoRow
import com.example.footballscoreapp.ui.theme.textColor
import com.example.footballscoreapp.ui.theme.textPadding

@Preview
@Composable
fun MatchAdditionalInfoCard(
    matchEntity: MatchEntity = myMatchEntityMock,
    matchDetailInfoEntity: MatchDetailInfoEntity? = mockDetailInfoEntity
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(categoriesInDetailsColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LeagueNameWithImageCard(
                modifier = Modifier
                    .fillMaxWidth(),
                leagueEntity = matchEntity.leagueInfo
            )
            MatchFullInfoCard(
                matchEntity = matchEntity,
                matchDetailInfoEntity = matchDetailInfoEntity
            )

        }

    }
}

@Preview
@Composable
private fun MatchFullInfoCard(
    matchEntity: MatchEntity = myMatchEntityMock,
    matchDetailInfoEntity: MatchDetailInfoEntity? = mockDetailInfoEntity
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(myMatchInAdditionalMatchInfoBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(paddingLeagueCardInfoRow)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                color = textColor,
                textAlign = TextAlign.Center,
                text = matchEntity.startTime.parseDateToStringFullDate()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(myMatchInAdditionalMatchInfoBackgroundColor),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamImageWithName(
                    modifier = Modifier.weight(1f),
                    imageUrl = matchEntity.homeTeamMatchInfoEntity.imageUrl,
                    teamName = matchEntity.homeTeamMatchInfoEntity.name,
                )
                val homeGoals by remember {
                    derivedStateOf {
                        if (matchEntity.homeTeamMatchInfoEntity.goals == null) {
                            "-"
                        } else matchEntity.homeTeamMatchInfoEntity.goals.toString()
                    }
                }
                val awayGoals by remember {
                    derivedStateOf {
                        if (matchEntity.awayTeamMatchInfoEntity.goals == null) {
                            "-"
                        } else matchEntity.awayTeamMatchInfoEntity.goals.toString()
                    }
                }

                Text(
                    modifier = Modifier.padding(end = textPadding),
                    color = if (matchEntity.status == MatchStatusEntity.STARTED)
                        onLiveScoreContent else textColor,
                    textAlign = TextAlign.Center,
                    fontSize = goalsFontSizeInDetails,
                    text = homeGoals
                )
                Text(
                    color = if (matchEntity.status == MatchStatusEntity.STARTED)
                        onLiveScoreContent else textColor,
                    textAlign = TextAlign.Center,
                    fontSize = goalsFontSizeInDetails,
                    text = ":"
                )
                Text(
                    modifier = Modifier.padding(start = textPadding),
                    color = if (matchEntity.status == MatchStatusEntity.STARTED)
                        onLiveScoreContent else textColor,
                    textAlign = TextAlign.Center,
                    fontSize = goalsFontSizeInDetails,
                    text = awayGoals
                )
                TeamImageWithName(
                    modifier = Modifier.weight(1f),
                    imageUrl = matchEntity.awayTeamMatchInfoEntity.imageUrl,
                    teamName = matchEntity.awayTeamMatchInfoEntity.name,
                )
            }
            matchDetailInfoEntity?.matchAdditionalInfoEntity?.arenaName?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = textColor,
                    text = stringResource(R.string.stadium, it)
                )
            }


        }

    }
}

@Preview
@Composable
private fun TeamImageWithName(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    teamName: String = "FC REALFFSDGDGG"
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(detailTeamImage)
                .padding(start = imagePadding, end = imagePadding),
            model = imageUrl,
            contentDescription = stringResource(R.string.league)
        )
        Text(
            modifier = Modifier.padding(10.dp),
            color = textColor,
            textAlign = TextAlign.Center,
            text = teamName
        )
    }
}


