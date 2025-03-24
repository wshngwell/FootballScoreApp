package com.example.footballscoreapp.presentation.leagueScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.footballscoreapp.R
import com.example.footballscoreapp.domain.entities.LeagueEntity
import com.example.footballscoreapp.presentation.myLeagueEntityMock
import com.example.footballscoreapp.ui.theme.leagueCardColor
import com.example.footballscoreapp.ui.theme.onLeagueColorContent

@Preview
@Composable
fun LeagueCard(
    leagueEntity: LeagueEntity = myLeagueEntityMock,
    onLeagueCardClicked: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clickable { onLeagueCardClicked() },
    ) {
        Row(
            modifier = Modifier
                .background(leagueCardColor)
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 5.dp, end = 5.dp),
                model = leagueEntity.leagueImageUrl,
                contentDescription = stringResource(R.string.league)
            )

            Text(
                modifier = Modifier.weight(1f),
                color = onLeagueColorContent,
                text = leagueEntity.leagueName
            )
        }

    }
}