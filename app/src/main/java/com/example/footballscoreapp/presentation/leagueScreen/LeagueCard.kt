package com.example.footballscoreapp.presentation.leagueScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.footballscoreapp.R
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.presentation.mathcesUi.MatchCard
import com.example.footballscoreapp.presentation.myLeaguesWithMatchesUIModelMock
import com.example.footballscoreapp.ui.theme.imagePadding
import com.example.footballscoreapp.ui.theme.imageSize
import com.example.footballscoreapp.ui.theme.leagueCardColorBackGround
import com.example.footballscoreapp.ui.theme.onLeagueColorContent
import com.example.footballscoreapp.ui.theme.paddingCard
import com.example.footballscoreapp.ui.theme.paddingLeagueCardInfoRow

@Preview
@Composable
fun LeagueCard(
    leagueWithMatchUIModel: LeaguesWithMatchesUIModel = myLeaguesWithMatchesUIModelMock,
    onMatchItemClicked: (MatchEntity) -> Unit = {},
    onAddOrDeleteMatchFromFavouriteClicked: (MatchEntity) -> Unit = {},
    onExpanded: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingCard, bottom = paddingCard)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .background(leagueCardColorBackGround)
                    .padding(paddingLeagueCardInfoRow)
                    .clickable {
                        onExpanded()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(imageSize)
                        .padding(start = imagePadding, end = imagePadding),
                    model = leagueWithMatchUIModel.league.leagueImageUrl,
                    contentDescription = stringResource(R.string.league)
                )

                Text(
                    modifier = Modifier.weight(1f),
                    color = onLeagueColorContent,
                    text = leagueWithMatchUIModel.league.leagueName
                )
            }

            AnimatedVisibility(
                visible = leagueWithMatchUIModel.isExpanded,
            ) {
                Column {
                    leagueWithMatchUIModel.matches.forEach {
                        MatchCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = paddingCard, bottom = paddingCard),
                            matchEntity = it,
                            onMatchCardClicked = onMatchItemClicked,
                            onAddOrDeleteMatchFromFavouriteClicked = {
                                onAddOrDeleteMatchFromFavouriteClicked(
                                    it
                                )
                            }
                        )

                    }
                }
            }
        }


    }

}
