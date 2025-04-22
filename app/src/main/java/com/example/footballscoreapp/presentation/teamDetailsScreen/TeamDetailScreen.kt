package com.example.footballscoreapp.presentation.teamDetailsScreen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.example.footballscoreapp.domain.entities.TResult
import com.example.footballscoreapp.domain.entities.teams.TeamMainInfoEntity
import com.example.footballscoreapp.domain.usecases.teamsFullInfoUseCases.GetTeamFullInfoUseCase
import com.example.footballscoreapp.presentation.GsonUtil.fromJson
import com.example.footballscoreapp.presentation.GsonUtil.toJson
import com.example.footballscoreapp.presentation.destinations.TeamDetailScreenDestination
import com.example.footballscoreapp.utils.myLog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get


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
    val teamMainInfoEntity = teamMainInfoEntityJson.fromJson<TeamMainInfoEntity>()

    val getTeamFullInfoUseCase = get<GetTeamFullInfoUseCase>()
    val scope = rememberCoroutineScope()
    scope.launch {
        val res = getTeamFullInfoUseCase(teamMainInfoEntity.id)
        when (res) {
            is TResult.Error -> myLog(res.exception.toString())
            is TResult.Success -> myLog(res.data.toString())
        }
    }

}