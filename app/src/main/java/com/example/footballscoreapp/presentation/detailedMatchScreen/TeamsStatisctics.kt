package com.example.footballscoreapp.presentation.detailedMatchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.footballscoreapp.R
import com.example.footballscoreapp.domain.entities.detailMatchInfo.matchStatistics.TeamStatisticsEntity
import com.example.footballscoreapp.presentation.mockDetailInfoEntity
import com.example.footballscoreapp.ui.theme.categoriesInDetailsColor
import com.example.footballscoreapp.ui.theme.lineUpCategorySize
import com.example.footballscoreapp.ui.theme.myLeagueInAdditionalMatchInfoBackgroundColor
import com.example.footballscoreapp.ui.theme.onBackGroundColor
import com.example.footballscoreapp.ui.theme.statisticItemSize


@Preview
@Composable
fun TeamsStatistics(
    teamStatisticsEntity: List<TeamStatisticsEntity>? = mockDetailInfoEntity.teamStatisticsEntity
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
            text = stringResource(R.string.statistic)
        )
        teamStatisticsEntity?.forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    color = onBackGroundColor,
                    textAlign = TextAlign.Center,
                    fontSize = statisticItemSize,
                    text = it.homeTeamResult
                )
                Text(
                    modifier = Modifier.weight(1f),
                    color = onBackGroundColor,
                    textAlign = TextAlign.Center,
                    fontSize = statisticItemSize,
                    text = it.type
                )
                Text(
                    modifier = Modifier.weight(1f),
                    color = onBackGroundColor,
                    textAlign = TextAlign.Center,
                    fontSize = statisticItemSize,
                    text = it.awayTeamResult
                )
            }
        }

    }

}
