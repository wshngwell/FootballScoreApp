package com.example.footballscoreapp.presentation.mathcesUi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.footballscoreapp.R
import com.example.footballscoreapp.domain.entities.MatchEntity
import com.example.footballscoreapp.presentation.myMatchEntityMock
import com.example.footballscoreapp.presentation.parseDateToString
import com.example.footballscoreapp.ui.theme.imagePadding
import com.example.footballscoreapp.ui.theme.imageSize
import com.example.footballscoreapp.ui.theme.matchColorCardBackGround
import com.example.footballscoreapp.ui.theme.matchTimeInfoPadding
import com.example.footballscoreapp.ui.theme.onLeagueColorContent

@Preview
@Composable
fun MatchCard(
    modifier: Modifier = Modifier,
    matchEntity: MatchEntity = myMatchEntityMock,
    onMatchCardClicked: (MatchEntity) -> Unit = {}
) {
    Card(
        modifier = modifier
            .clickable { onMatchCardClicked(matchEntity) },
    ) {
        Column(
            modifier = Modifier
                .background(matchColorCardBackGround)
        ) {
            Row(
                modifier = Modifier
            ) {
                Icon(
                    modifier = Modifier
                        .size(imageSize)
                        .padding(start = imagePadding, end = imagePadding)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Outlined.Star,
                    contentDescription = stringResource(R.string.favourite_match)
                )
                Column {
                    TeamMainInfo(
                        modifier = Modifier.padding(5.dp),
                        imageUrl = matchEntity.homeTeamMatchInfo.imageUrl,
                        teamName = matchEntity.homeTeamMatchInfo.name,
                        teamGoals = matchEntity.homeTeamMatchInfo.goals
                    )

                    TeamMainInfo(
                        modifier = Modifier.padding(5.dp),
                        imageUrl = matchEntity.awayTeamMatchInfo.imageUrl,
                        teamName = matchEntity.awayTeamMatchInfo.name,
                        teamGoals = matchEntity.awayTeamMatchInfo.goals
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = matchTimeInfoPadding, end = matchTimeInfoPadding),
            ) {
                Spacer(modifier = Modifier.size(40.dp))
                Text(
                    color = onLeagueColorContent,
                    text = matchEntity.startTime.parseDateToString()
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    textAlign = TextAlign.Center,
                    color = onLeagueColorContent,
                    text = stringResource(matchEntity.status.parseMatchStatusInfoToString())
                )
            }

        }


    }
}

@Composable
@Preview
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
    ) {
        MatchCard(modifier = Modifier.background(Color.Yellow))
        Text("базовый")

        MatchCard(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
        )
        Text("на всю ширину")

        Row {
            MatchCard(
                modifier = Modifier
                    .background(Color.Red)
                    .weight(1f)
            )
            MatchCard(
                modifier = Modifier
                    .background(Color.Yellow)
                    .weight(1f)
            )
        }
        Text("2 вью на всю ширину экрана")
    }
}

@Composable
private fun TeamMainInfo(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    teamName: String = "",
    teamGoals: Int? = 0
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(40.dp)
                .padding(end = 10.dp),
            model = imageUrl,
            contentDescription = stringResource(R.string.league)
        )

        Text(
            modifier = Modifier.weight(1f),
            color = onLeagueColorContent,
            text = teamName
        )
        if (teamGoals != null) {
            Text(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                color = onLeagueColorContent,
                text = teamGoals.toString()
            )
        }

    }
}