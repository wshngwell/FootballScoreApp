package com.example.footballscoreapp.presentation.AllMatchesScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.footballscoreapp.R
import com.example.footballscoreapp.domain.entities.matches.LeagueEntity
import com.example.footballscoreapp.presentation.myLeagueEntityMock
import com.example.footballscoreapp.ui.theme.imagePadding
import com.example.footballscoreapp.ui.theme.imageSize
import com.example.footballscoreapp.ui.theme.textColor

@Preview
@Composable
fun LeagueNameWithImageCard(
    modifier: Modifier = Modifier,
    leagueEntity: LeagueEntity = myLeagueEntityMock
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(imageSize)
                .padding(start = imagePadding, end = imagePadding),
            model = leagueEntity.leagueImageUrl,
            contentDescription = stringResource(R.string.league)
        )

        Text(
            modifier = Modifier.weight(1f),
            color = textColor,
            text = leagueEntity.leagueName
        )
    }
}