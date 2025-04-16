package com.example.footballscoreapp.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.footballscoreapp.currentLang
import com.example.footballscoreapp.currentTheme
import com.example.footballscoreapp.getCurrentTheme

@SuppressLint("RememberReturnType")
@Composable
fun FootballScoreAppTheme(
    content: @Composable () -> Unit
) {


    val context = LocalContext.current

    val isDarkTheme = isSystemInDarkTheme()

    remember { getCurrentTheme(context, isDarkTheme) }

    val colorScheme by remember(
        currentTheme.collectAsStateWithLifecycle().value.isSystemDark,
        currentLang.collectAsStateWithLifecycle().value.locale
    ) {
        mutableStateOf(
            darkColorScheme()
        )
    }

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
