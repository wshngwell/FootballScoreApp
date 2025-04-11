package com.example.footballscoreapp.presentation.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.footballscoreapp.presentation.NavGraphs
import com.example.footballscoreapp.presentation.appCurrentDestinationAsState
import com.example.footballscoreapp.presentation.destinations.Destination
import com.example.footballscoreapp.presentation.startAppDestination
import com.example.footballscoreapp.ui.theme.bottomBarBackGroundColor
import com.example.footballscoreapp.ui.theme.bottomBarIconSize
import com.example.footballscoreapp.ui.theme.bottomNavigationBarHeight
import com.example.footballscoreapp.ui.theme.bottomNavigationFontSize
import com.example.footballscoreapp.ui.theme.bottomNavigationPadding
import com.example.footballscoreapp.ui.theme.selectedBottomBarColor
import com.example.footballscoreapp.ui.theme.unselectedBottomBarColor
import com.ramcosta.composedestinations.navigation.navigate


@Composable
fun BottomBar(
    navController: NavController,
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    val shouldBottomBarBeVisible by remember(currentDestination.route) {
        BottomBarsElementEnum.entries.any {
            it.destination.route == currentDestination.route
        }.let {
            mutableStateOf(it)
        }
    }
    if (shouldBottomBarBeVisible) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomNavigationBarHeight)
                .background(bottomBarBackGroundColor)
                .padding(bottomNavigationPadding)
        ) {
            BottomBarsElementEnum.entries.forEach {
                val isSelected by remember(currentDestination) {
                    mutableStateOf(currentDestination == it.destination)
                }
                val contentColor by remember(currentDestination) {
                    mutableStateOf(
                        if (isSelected) {
                            selectedBottomBarColor
                        } else {
                            unselectedBottomBarColor
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                        .clickable {
                            if (currentDestination.route != it.destination.route) {
                                navController.navigate(
                                    it.destination,
                                    navOptionsBuilder = {
                                        launchSingleTop = true
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            inclusive = false
                                        }
                                    }
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .size(bottomBarIconSize),
                            painter = painterResource(it.iconFromResources),
                            contentDescription = stringResource(it.textResources),
                            colorFilter = ColorFilter.tint(
                                color = if (currentDestination == it.destination) {
                                    Color.White
                                } else Color.Black
                            )
                        )
                        Text(
                            stringResource(it.textResources),
                            fontSize = bottomNavigationFontSize,
                            color = contentColor
                        )
                    }

                }

            }
        }
    }


}