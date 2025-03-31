package com.example.footballscoreapp.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val colorScheme = darkColorScheme()


@Composable
fun FootballScoreAppTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = myBackGround.toArgb()// цвет АппБара
            window.navigationBarColor = myBackGround.toArgb() // цвет контрль панели
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                false // светлый или темный цвет для АппБара
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                false // для контроль панели
        }
    }
}
