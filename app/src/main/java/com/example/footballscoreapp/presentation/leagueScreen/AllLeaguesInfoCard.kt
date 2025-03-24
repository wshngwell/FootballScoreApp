package com.example.footballscoreapp.presentation.leagueScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.footballscoreapp.R
import com.example.footballscoreapp.ui.theme.allGamesInfoBackGround
import com.example.footballscoreapp.ui.theme.onLeagueColorContent


@Preview
@Composable
fun AllLeaguesInfoCard(
    gamesCount: Int = 0
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        Row(
            modifier = Modifier
                .background(allGamesInfoBackGround)
                .padding(10.dp),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                color = onLeagueColorContent,
                text = stringResource(R.string.all_games)
            )
            Text(
                color = onLeagueColorContent,
                text = "$gamesCount"
            )

        }
    }
}