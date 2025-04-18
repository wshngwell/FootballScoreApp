package com.example.footballscoreapp.presentation.settingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.footballscoreapp.R
import com.example.footballscoreapp.ui.theme.alertDialogOptionElevation
import com.example.footballscoreapp.ui.theme.alertDialogSpacerSize
import com.example.footballscoreapp.ui.theme.closeButtonAlertDialogPadding
import com.example.footballscoreapp.ui.theme.paddingInSettingsOptionsText
import com.example.footballscoreapp.ui.theme.settingDialogsCornersPercent
import com.example.footballscoreapp.ui.theme.settingsHeaderInDialogFontSize
import com.example.footballscoreapp.ui.theme.textColor
import com.example.footballscoreapp.ui.theme.themeCategoriesFontSize
import com.example.footballscoreapp.ui.theme.themesModeOptionsBackGround
import com.example.footballscoreapp.ui.theme.themesModeOptionsBackGroundSelected


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SettingsAlertDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onOptionClicked: (Int) -> Unit = {},
    optionsListIds: List<Int> = listOf(),
    mainText: String = "",
    selectedOption: String = ""
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
                color = textColor
            )
            Spacer(modifier = Modifier.height(alertDialogSpacerSize))
            Column(modifier = Modifier.clip(RoundedCornerShape(settingDialogsCornersPercent))) {
                optionsListIds.forEach {
                    val stringOption = stringResource(it)
                    SettingsAlertDialogOption(optionName = stringOption,
                        isSelected = selectedOption == stringOption,
                        onClick = {
                            onOptionClicked(it)
                            onDismiss()
                        }
                    )
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
                color = textColor
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
            color = textColor
        )
    }

}