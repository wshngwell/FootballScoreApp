package com.example.footballscoreapp.presentation.detailedMatchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.footballscoreapp.R
import com.example.footballscoreapp.domain.entities.detailMatchInfo.additionalMatchInfo.CoachEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.additionalMatchInfo.MatchAdditionalInfoEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.lineup.LineUpEntity
import com.example.footballscoreapp.domain.entities.detailMatchInfo.lineup.TeamLineUpInfoEntity
import com.example.footballscoreapp.presentation.mockDetailInfoEntity
import com.example.footballscoreapp.presentation.mockMatchAdditionalInfo
import com.example.footballscoreapp.ui.theme.categoriesInDetailsColor
import com.example.footballscoreapp.ui.theme.imagePadding
import com.example.footballscoreapp.ui.theme.lineUpCategorySize
import com.example.footballscoreapp.ui.theme.myLeagueInAdditionalMatchInfoBackgroundColor
import com.example.footballscoreapp.ui.theme.onBackGroundColor
import com.example.footballscoreapp.ui.theme.paddingAllLeaguesInfoRow
import com.example.footballscoreapp.ui.theme.playerOrCoachSize

@Preview
@Composable
fun TeamsLineUps(
    matchAdditionalInfo: MatchAdditionalInfoEntity? = mockMatchAdditionalInfo,
    lineUpEntity: LineUpEntity? = mockDetailInfoEntity.lineUpEntity
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(myLeagueInAdditionalMatchInfoBackgroundColor)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(categoriesInDetailsColor),
            color = onBackGroundColor,
            textAlign = TextAlign.Center,
            fontSize = lineUpCategorySize,
            text = stringResource(R.string.mainLineUp)
        )
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                CoachCard(coachEntity = matchAdditionalInfo?.homeCoach)
                TeamListOfPlayers(
                    teamLineUpInfoEntity = lineUpEntity?.homeTeam,
                    isSubstitute = false
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CoachCard(coachEntity = matchAdditionalInfo?.awayCoach)
                TeamListOfPlayers(
                    teamLineUpInfoEntity = lineUpEntity?.awayTeam,
                    isSubstitute = false
                )

            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(categoriesInDetailsColor),
            color = onBackGroundColor,
            textAlign = TextAlign.Center,
            fontSize = lineUpCategorySize,
            text = stringResource(R.string.substiture)
        )
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                TeamListOfPlayers(
                    teamLineUpInfoEntity = lineUpEntity?.homeTeam,
                    isSubstitute = true
                )

            }
            Column(
                modifier = Modifier.weight(1f),
            ) {
                TeamListOfPlayers(
                    teamLineUpInfoEntity = lineUpEntity?.awayTeam,
                    isSubstitute = true
                )

            }

        }
    }
}


@Composable
private fun CoachCard(
    coachEntity: CoachEntity?
) {
    coachEntity?.let {
        NameWithImageOfCoachOrPlayer(
            imageUrl = it.coachImageUrl,
            name = stringResource(R.string.coach, it.coachName)
        )
    }
}

@Composable
private fun TeamListOfPlayers(
    teamLineUpInfoEntity: TeamLineUpInfoEntity?,
    isSubstitute: Boolean = false

) {
    teamLineUpInfoEntity?.players?.forEach {
        if (it.substitute == isSubstitute) {
            NameWithImageOfCoachOrPlayer(
                imageUrl = it.playerImageUrl,
                name = it.playerName
            )
        }

    }
}


@Preview
@Composable
private fun NameWithImageOfCoachOrPlayer(
    imageUrl: String = "",
    name: String = "Andrey"
) {
    Row(
        modifier = Modifier.padding(paddingAllLeaguesInfoRow),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(playerOrCoachSize)
                .padding(imagePadding)
                .clip(RoundedCornerShape(100)),
            model = imageUrl,
            contentDescription = stringResource(R.string.league)
        )
        Text(
            modifier = Modifier.weight(1f),
            color = onBackGroundColor,
            text = name
        )
    }
}

