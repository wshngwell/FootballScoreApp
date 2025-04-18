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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.footballscoreapp.LanguageType
import com.example.footballscoreapp.R
import com.example.footballscoreapp.ThemeType
import com.example.footballscoreapp.currentLang
import com.example.footballscoreapp.currentTheme
import com.example.footballscoreapp.saveCurrentTheme
import com.example.footballscoreapp.saveLanguage
import com.example.footballscoreapp.ui.theme.alertDialogSpacerSize
import com.example.footballscoreapp.ui.theme.myBackGround
import com.example.footballscoreapp.ui.theme.paddingStartTextSettingsFromImage
import com.example.footballscoreapp.ui.theme.screenTopPadding
import com.example.footballscoreapp.ui.theme.settingDialogsCornersPercent
import com.example.footballscoreapp.ui.theme.settingIconSize
import com.example.footballscoreapp.ui.theme.settingOptionCardColorBackground
import com.example.footballscoreapp.ui.theme.settingsCategoriesFontSize
import com.example.footballscoreapp.ui.theme.settingsDialogPadding
import com.example.footballscoreapp.ui.theme.settingsOptionPadding
import com.example.footballscoreapp.ui.theme.textColor
import com.example.footballscoreapp.ui.theme.themeChangerIconColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph


@RootNavGraph
@Destination
@Composable
fun SettingsScreen() {

    var shouldThemeChangeDialogBeVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var shouldLangChangeDialogBeVisible by rememberSaveable {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(myBackGround)
            .padding(top = screenTopPadding),

        ) {
        Column {
            SettingCard(
                image = painterResource(id = R.drawable.theme_change_icon),
                text = stringResource(id = R.string.change_theme_scheme),
                makeDialogBeVisible = { shouldThemeChangeDialogBeVisible = true }
            )
            Spacer(modifier = Modifier.height(alertDialogSpacerSize))
            SettingCard(
                image = painterResource(id = R.drawable.language_change_icon),
                text = stringResource(id = R.string.change_lang),
                makeDialogBeVisible = { shouldLangChangeDialogBeVisible = true }
            )
        }
        val context = LocalContext.current

        if (shouldThemeChangeDialogBeVisible) {
            val themeState = currentTheme.collectAsStateWithLifecycle().value
            val isDarkTheme = isSystemInDarkTheme()

            val themesNamesResIds = ThemeType.entries.map { it.textResources }

            SettingsAlertDialog(
                modifier = Modifier
                    .clip(RoundedCornerShape(settingDialogsCornersPercent))
                    .background(settingOptionCardColorBackground)
                    .padding(settingsDialogPadding),
                onDismiss = {
                    shouldThemeChangeDialogBeVisible = false
                },
                optionsListIds = themesNamesResIds,
                onOptionClicked = { optionName ->
                    saveCurrentTheme(
                        context = context,
                        themeType = ThemeType.entries.find { it.textResources == optionName }
                            ?: ThemeType.DARK,
                        isDarkTheme = when (themeState.themeType) {
                            ThemeType.LIGHT -> false
                            ThemeType.DARK -> true
                            ThemeType.SYSTEM -> isDarkTheme
                        }
                    )
                },
                selectedOption = stringResource(themeState.themeType.textResources),
                mainText = stringResource(R.string.change_theme_scheme)
            )
        }
        if (shouldLangChangeDialogBeVisible) {

            val langState = currentLang.collectAsStateWithLifecycle().value

            val languageNamesResIds = LanguageType.entries.map { it.textResources }

            SettingsAlertDialog(
                modifier = Modifier
                    .clip(RoundedCornerShape(settingDialogsCornersPercent))
                    .background(settingOptionCardColorBackground)
                    .padding(settingsDialogPadding),
                onDismiss = {
                    shouldLangChangeDialogBeVisible = false
                },
                onOptionClicked = { optionClicked ->
                    saveLanguage(
                        context = context,
                        languageType = LanguageType.entries.find { it.textResources == optionClicked }
                            ?: LanguageType.RUSSIAN
                    )
                },
                selectedOption = stringResource(langState.textResources),
                optionsListIds = languageNamesResIds,
                mainText = stringResource(id = R.string.change_lang)
            )
        }
    }
}

@Preview
@Composable
private fun SettingCard(
    image: Painter? = null,
    text: String = "",
    makeDialogBeVisible: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable { makeDialogBeVisible() }
            .fillMaxWidth()
            .background(settingOptionCardColorBackground)
            .padding(settingsOptionPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        image?.let {
            Image(
                modifier = Modifier.size(settingIconSize),
                painter = image,
                contentDescription = text,
                colorFilter = ColorFilter.tint(themeChangerIconColor)
            )
            Text(
                modifier = Modifier
                    .padding(start = paddingStartTextSettingsFromImage),
                text = text,
                color = textColor,
                fontSize = settingsCategoriesFontSize
            )
        }

    }
}


