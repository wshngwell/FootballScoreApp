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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.domain.entities.matches.MatchStatusEntity
import com.example.footballscoreapp.presentation.myMatchEntityMock
import com.example.footballscoreapp.presentation.parseDateToString
import com.example.footballscoreapp.ui.theme.goalsFontSize
import com.example.footballscoreapp.ui.theme.goalsStartAndEndPadding
import com.example.footballscoreapp.ui.theme.imagePadding
import com.example.footballscoreapp.ui.theme.imageSize
import com.example.footballscoreapp.ui.theme.matchColorCardBackGround
import com.example.footballscoreapp.ui.theme.matchTimeInfoPadding
import com.example.footballscoreapp.ui.theme.onDefaultMatchColorContent
import com.example.footballscoreapp.ui.theme.onMatchLiveCardColorContent
import com.example.footballscoreapp.ui.theme.onMatchNotLiveCardColorContent
import com.example.footballscoreapp.ui.theme.screenStartOrEndPadding

@Preview
@Composable
fun MatchCard(
    modifier: Modifier = Modifier,
    matchEntity: MatchEntity = myMatchEntityMock,
    onAddOrDeleteMatchFromFavouriteClicked: () -> Unit = {},
    onMatchCardClicked: (MatchEntity) -> Unit = {}
) {
    val color = if (matchEntity.status == MatchStatusEntity.STARTED) {
        onMatchLiveCardColorContent
    } else onMatchNotLiveCardColorContent

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
                        .clickable { onAddOrDeleteMatchFromFavouriteClicked() }
                        .align(Alignment.CenterVertically),
                    imageVector = if (matchEntity.isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(R.string.favourite_match)
                )

                Column {

                    TeamMainInfo(
                        modifier = Modifier.padding(5.dp),
                        imageUrl = matchEntity.homeTeamMatchInfo.imageUrl,
                        teamName = matchEntity.homeTeamMatchInfo.name,
                        teamGoals = matchEntity.homeTeamMatchInfo.goals,
                        color = color
                    )

                    TeamMainInfo(
                        modifier = Modifier.padding(5.dp),
                        imageUrl = matchEntity.awayTeamMatchInfo.imageUrl,
                        teamName = matchEntity.awayTeamMatchInfo.name,
                        teamGoals = matchEntity.awayTeamMatchInfo.goals,
                        color = color
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
                    color = color,
                    text = matchEntity.startTime.parseDateToString()
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    textAlign = TextAlign.Center,
                    color = color,
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
    teamGoals: Int? = 0,
    color: Color = Color.White
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(imageSize)
                .padding(end = screenStartOrEndPadding),
            model = imageUrl,
            contentDescription = stringResource(R.string.league)
        )

        Text(
            modifier = Modifier.weight(1f),
            color = onDefaultMatchColorContent,
            text = teamName
        )
        if (teamGoals != null) {
            Text(
                modifier = Modifier.padding(
                    start = goalsStartAndEndPadding,
                    end = goalsStartAndEndPadding
                ),
                color = color,
                fontSize = goalsFontSize,
                text = teamGoals.toString()
            )
        }

    }
}