package com.example.footballscoreapp.presentation.leagueScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.footballscoreapp.domain.entities.matches.MatchEntity
import com.example.footballscoreapp.presentation.mathcesUi.MatchCard
import com.example.footballscoreapp.presentation.myLeaguesWithMatchesUIModelMock
import com.example.footballscoreapp.ui.theme.leagueCardColorBackGround
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
            LeagueNameWithImageCard(
                modifier = Modifier
                    .background(leagueCardColorBackGround)
                    .padding(paddingLeagueCardInfoRow)
                    .clickable {
                        onExpanded()
                    },
                leagueEntity = leagueWithMatchUIModel.league
            )
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
