package com.example.footballscoreapp.presentation.teamDetailsScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.footballscoreapp.R
import com.example.footballscoreapp.di.paramsForTeamDetailViewModel
import com.example.footballscoreapp.domain.entities.teams.TeamFullInfoEntity
import com.example.footballscoreapp.domain.entities.teams.TeamMainInfoEntity
import com.example.footballscoreapp.presentation.GsonUtil.fromJson
import com.example.footballscoreapp.presentation.GsonUtil.toJson
import com.example.footballscoreapp.presentation.MyProgressbar
import com.example.footballscoreapp.presentation.destinations.TeamDetailScreenDestination
import com.example.footballscoreapp.presentation.mockTeamFullInfoEntity
import com.example.footballscoreapp.presentation.mockTeamMainInfoEntity
import com.example.footballscoreapp.presentation.parseDateToStringFullDateWithoutHours
import com.example.footballscoreapp.presentation.teamDetailsScreen.TeamDetailsViewModel.Event
import com.example.footballscoreapp.presentation.teamDetailsScreen.TeamDetailsViewModel.Intent
import com.example.footballscoreapp.presentation.teamDetailsScreen.TeamDetailsViewModel.State
import com.example.footballscoreapp.presentation.theme.categoryNameInTeamDetailsScreen
import com.example.footballscoreapp.presentation.theme.detailTeamScreenImage
import com.example.footballscoreapp.presentation.theme.fullTeamCategoryInfoBackGroundColor
import com.example.footballscoreapp.presentation.theme.screenTopPadding
import com.example.footballscoreapp.presentation.theme.screenTopPaddingInTeamDetailsScreen
import com.example.footballscoreapp.presentation.theme.teamMainInfoColor
import com.example.footballscoreapp.presentation.theme.teamNameInTeamDetailsScreen
import com.example.footballscoreapp.presentation.theme.textColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel


fun getTeamDetailsDestination(teamMainInfoEntity: TeamMainInfoEntity) = TeamDetailScreenDestination(
    teamMainInfoEntityJson = teamMainInfoEntity.toJson()
)

@SuppressLint("CoroutineCreationDuringComposition")
@RootNavGraph
@Destination
@Composable
fun TeamDetailScreen(
    teamMainInfoEntityJson: String,
    navigator: DestinationsNavigator
) {
    val viewModel = koinViewModel<TeamDetailsViewModel>(
        parameters = {
            val teamMainInfoEntity = teamMainInfoEntityJson.fromJson<TeamMainInfoEntity>()
            paramsForTeamDetailViewModel(teamMainInfoEntity)
        }
    )
    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (Intent) -> Unit by remember {
        mutableStateOf(viewModel::sendIntent)
    }
    val event: Flow<Event> by remember {
        mutableStateOf(viewModel.event)
    }
    LaunchedEffect(Unit) {
        event.filterIsInstance<Event>().collect {}
    }
    UI(
        state = state,
        intent = intent
    )
}

@Preview
@Composable
private fun UI(
    state: State = State(teamMainInfoEntity = mockTeamMainInfoEntity),
    intent: (Intent) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (state.isLoading) {
            MyProgressbar(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (state.error != null) {
            TeamDetailsContent(
                teamMainInfoEntity = state.teamMainInfoEntity,
                teamFullInfoEntity = null
            )
        } else {
            TeamDetailsContent(
                teamMainInfoEntity = state.teamMainInfoEntity,
                teamFullInfoEntity = state.teamFullInfoEntity
            )
        }

    }
}


@Preview
@Composable
private fun TeamDetailsContent(
    teamMainInfoEntity: TeamMainInfoEntity = mockTeamMainInfoEntity,
    teamFullInfoEntity: TeamFullInfoEntity? = mockTeamFullInfoEntity,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        ImageWithNameAndCategory(
            categoryText = stringResource(R.string.team_info),
            mainText = teamMainInfoEntity.name,
            imageUrl = teamMainInfoEntity.imageUrl,
            contentColorBackground = teamMainInfoColor
        )
        teamFullInfoEntity?.let {

            ImageWithNameAndCategory(
                categoryText = stringResource(R.string.stadium),
                mainText = it.arenaName,
                imageUrl = it.arenaImageUrl,
                contentColorBackground = getFullTeamInfoBackGroundColor(it.genderEntity)
            )
            ImageWithNameAndCategory(
                categoryText = stringResource(R.string.country_name),
                mainText = it.countryName,
                imageUrl = it.countryImageUrl,
                contentColorBackground = getFullTeamInfoBackGroundColor(it.genderEntity)
            )
            TextWithCategory(
                categoryText = stringResource(R.string.foundation_date),
                valueText = it.foundationDate.parseDateToStringFullDateWithoutHours(),
                valueTextColorBackground = getFullTeamInfoBackGroundColor(it.genderEntity)
            )
            TextWithCategory(
                categoryText = stringResource(R.string.tournament_name),
                valueText = it.tournamentName,
                valueTextColorBackground = getFullTeamInfoBackGroundColor(it.genderEntity)
            )
        }
    }
}


@Preview
@Composable
private fun TextWithCategory(
    modifier: Modifier = Modifier,
    categoryText: String = "",
    valueText: String = "",
    valueTextColorBackground: Color = Color.Black
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(fullTeamCategoryInfoBackGroundColor)
                .padding(screenTopPaddingInTeamDetailsScreen),
            text = categoryText,
            textAlign = TextAlign.Center,
            color = textColor,
            fontSize = categoryNameInTeamDetailsScreen
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(valueTextColorBackground)
                .padding(top = screenTopPadding, bottom = screenTopPadding),
            text = valueText,
            textAlign = TextAlign.Center,
            color = textColor,
            fontSize = teamNameInTeamDetailsScreen
        )
    }
}

@Preview
@Composable
private fun ImageWithNameAndCategory(
    modifier: Modifier = Modifier,
    categoryText: String = "",
    mainText: String = "",
    imageUrl: String = "",
    contentColorBackground: Color = Color.White,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(fullTeamCategoryInfoBackGroundColor)
                .padding(screenTopPaddingInTeamDetailsScreen),
            text = categoryText,
            textAlign = TextAlign.Center,
            color = textColor,
            fontSize = categoryNameInTeamDetailsScreen
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(contentColorBackground)
                .padding(top = screenTopPadding, bottom = screenTopPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = mainText,
                textAlign = TextAlign.Center,
                color = textColor,
                fontSize = teamNameInTeamDetailsScreen
            )
            AsyncImage(
                modifier = Modifier
                    .size(detailTeamScreenImage)
                    .weight(1f),
                model = imageUrl,
                contentDescription = stringResource(R.string.categoryteaminfo)
            )
        }
    }
}