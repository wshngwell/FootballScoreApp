package com.example.footballscoreapp.presentation.settingsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.footballscoreapp.R
import com.example.footballscoreapp.ThemeType
import com.example.footballscoreapp.saveCurrentTheme
import com.example.footballscoreapp.ui.theme.alertDialogOptionElevation
import com.example.footballscoreapp.ui.theme.alertDialogSpacerSize
import com.example.footballscoreapp.ui.theme.closeButtonAlertDialogPadding
import com.example.footballscoreapp.ui.theme.myBackGround
import com.example.footballscoreapp.ui.theme.onLeagueColorContent
import com.example.footballscoreapp.ui.theme.paddingInSettingsOptionsText
import com.example.footballscoreapp.ui.theme.paddingStartTextSettingsFromImage
import com.example.footballscoreapp.ui.theme.screenTopPadding
import com.example.footballscoreapp.ui.theme.settingDialogsCornersPercent
import com.example.footballscoreapp.ui.theme.settingIconSize
import com.example.footballscoreapp.ui.theme.settingOptionCardColorBackground
import com.example.footballscoreapp.ui.theme.settingsCategoriesFontSize
import com.example.footballscoreapp.ui.theme.settingsDialogPadding
import com.example.footballscoreapp.ui.theme.settingsHeaderInDialogFontSize
import com.example.footballscoreapp.ui.theme.settingsOptionPadding
import com.example.footballscoreapp.ui.theme.themeCategoriesFontSize
import com.example.footballscoreapp.ui.theme.themeChangerIconColor
import com.example.footballscoreapp.ui.theme.themesModeOptionsBackGround
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph


@RootNavGraph
@Destination
@Composable
fun SettingsScreen() {

    var shouldThemeChangeDialogBeVisible by rememberSaveable {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(myBackGround)
            .padding(top = screenTopPadding),

        ) {
        Column {

            Row(
                modifier = Modifier
                    .clickable { shouldThemeChangeDialogBeVisible = true }
                    .fillMaxWidth()
                    .background(settingOptionCardColorBackground)
                    .padding(settingsOptionPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(settingIconSize),
                    painter = painterResource(id = R.drawable.theme_change_icon),
                    contentDescription = stringResource(R.string.theme_change_image),
                    colorFilter = ColorFilter.tint(themeChangerIconColor)
                )
                Text(
                    modifier = Modifier
                        .padding(start = paddingStartTextSettingsFromImage),
                    text = stringResource(R.string.change_theme_scheme),
                    color = onLeagueColorContent,
                    fontSize = settingsCategoriesFontSize
                )
            }
        }
        if (shouldThemeChangeDialogBeVisible) {
            ThemeChangeAlertDialog(
                modifier = Modifier
                    .clip(RoundedCornerShape(settingDialogsCornersPercent))
                    .background(settingOptionCardColorBackground)
                    .padding(settingsDialogPadding),
                onDismiss = {
                    shouldThemeChangeDialogBeVisible = false
                }
            )
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeChangeAlertDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.change_theme_scheme),
                fontSize = settingsHeaderInDialogFontSize,
                color = onLeagueColorContent
            )
            Spacer(modifier = Modifier.height(alertDialogSpacerSize))
            Column(modifier = Modifier.clip(RoundedCornerShape(settingDialogsCornersPercent))) {
                ThemeModeOption(optionName = stringResource(R.string.white_option),
                    onClick = {
                        saveCurrentTheme(
                            context = context,
                            themeType = ThemeType.LIGHT,
                            isDarkTheme = false
                        )
                        onDismiss()
                    }
                )
                ThemeModeOption(optionName = stringResource(R.string.black_option),
                    onClick = {
                        saveCurrentTheme(
                            context = context,
                            themeType = ThemeType.DARK,
                            isDarkTheme = true
                        )
                        onDismiss()
                    }
                )
                ThemeModeOption(optionName = stringResource(R.string.system_option),
                    onClick = {
                        saveCurrentTheme(
                            context = context,
                            themeType = ThemeType.SYSTEM,
                            isDarkTheme = isDarkTheme
                        )
                        onDismiss()
                    }
                )
            }
            Spacer(modifier = Modifier.height(alertDialogSpacerSize))
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onDismiss() }
                    .padding(closeButtonAlertDialogPadding),
                text = stringResource(R.string.close),
                fontSize = themeCategoriesFontSize,
                color = onLeagueColorContent
            )
        }

    }
}

@Preview
@Composable
private fun ThemeModeOption(
    optionName: String = "",
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = alertDialogOptionElevation)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(themesModeOptionsBackGround)
                .padding(paddingInSettingsOptionsText),
            text = optionName,
            textAlign = TextAlign.Center,
            fontSize = themeCategoriesFontSize,
            color = onLeagueColorContent
        )
    }

}