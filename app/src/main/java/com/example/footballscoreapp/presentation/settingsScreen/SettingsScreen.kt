package com.example.footballscoreapp.presentation.settingsScreen

import android.content.Context
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.footballscoreapp.LanguageType
import com.example.footballscoreapp.R
import com.example.footballscoreapp.ThemeState
import com.example.footballscoreapp.ThemeType
import com.example.footballscoreapp.currentLang
import com.example.footballscoreapp.currentTheme
import com.example.footballscoreapp.saveCurrentTheme
import com.example.footballscoreapp.saveLanguage
import com.example.footballscoreapp.ui.theme.alertDialogOptionElevation
import com.example.footballscoreapp.ui.theme.alertDialogSpacerSize
import com.example.footballscoreapp.ui.theme.closeButtonAlertDialogPadding
import com.example.footballscoreapp.ui.theme.myBackGround
import com.example.footballscoreapp.ui.theme.onBackGroundColor
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
import com.example.footballscoreapp.ui.theme.themesModeOptionsBackGroundSelected
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
        if (shouldThemeChangeDialogBeVisible) {
            val themeState = currentTheme.collectAsStateWithLifecycle().value
            val isDarkTheme = isSystemInDarkTheme()

            ThemeChangeAlertDialog(
                modifier = Modifier
                    .clip(RoundedCornerShape(settingDialogsCornersPercent))
                    .background(settingOptionCardColorBackground)
                    .padding(settingsDialogPadding),
                onDismiss = {
                    shouldThemeChangeDialogBeVisible = false
                },
                themeState = themeState,
                isDarkTheme = isDarkTheme,
                mainText = stringResource(R.string.change_theme_scheme)
            )
        }
        if (shouldLangChangeDialogBeVisible) {

            val langState = currentLang.collectAsStateWithLifecycle().value

            ThemeChangeAlertDialog(
                modifier = Modifier
                    .clip(RoundedCornerShape(settingDialogsCornersPercent))
                    .background(settingOptionCardColorBackground)
                    .padding(settingsDialogPadding),
                onDismiss = {
                    shouldLangChangeDialogBeVisible = false
                },
                currentLangState = langState,
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
                color = onBackGroundColor,
                fontSize = settingsCategoriesFontSize
            )
        }

    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeChangeAlertDialog(
    themeState: ThemeState? = null,
    currentLangState: LanguageType? = null,
    isDarkTheme: Boolean? = null,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    mainText: String = "",
    onDismiss: () -> Unit = {}
) {

    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = mainText,
                fontSize = settingsHeaderInDialogFontSize,
                color = onBackGroundColor
            )
            Spacer(modifier = Modifier.height(alertDialogSpacerSize))
            Column(modifier = Modifier.clip(RoundedCornerShape(settingDialogsCornersPercent))) {
                themeState?.let {
                    ThemeType.entries.forEach {
                        SettingsAlertDialogOption(optionName = stringResource(it.textResources),
                            isSelected = themeState.themeType == it,
                            onClick = {
                                saveCurrentTheme(
                                    context = context,
                                    themeType = it,
                                    isDarkTheme = when (it) {
                                        ThemeType.LIGHT -> false
                                        ThemeType.DARK -> true
                                        ThemeType.SYSTEM -> isDarkTheme!!
                                    }
                                )
                                onDismiss()
                            }
                        )
                    }
                }
                currentLangState?.let {
                    LanguageType.entries.forEach {
                        SettingsAlertDialogOption(optionName = stringResource(it.textResources),
                            isSelected = currentLangState == it,
                            onClick = {
                                saveLanguage(
                                    context = context,
                                    languageType = it
                                )
                                onDismiss()
                            }
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(alertDialogSpacerSize))
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onDismiss() }
                    .padding(closeButtonAlertDialogPadding),
                text = stringResource(R.string.close),
                fontSize = themeCategoriesFontSize,
                color = onBackGroundColor
            )
        }

    }
}

@Preview
@Composable
private fun SettingsAlertDialogOption(
    optionName: String = "",
    isSelected: Boolean = false,
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
                .background(if (isSelected) themesModeOptionsBackGroundSelected else themesModeOptionsBackGround)
                .padding(paddingInSettingsOptionsText),
            text = optionName,
            textAlign = TextAlign.Center,
            fontSize = themeCategoriesFontSize,
            color = onBackGroundColor
        )
    }

}