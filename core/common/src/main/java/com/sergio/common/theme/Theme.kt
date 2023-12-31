package com.sergio.common.theme

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val lightColorScheme = lightColorScheme(
    primary = LightPurple,
    onPrimary = Color.White,
    secondary = Color.White,
    onSecondary = Color.Black,
    tertiary = PastelGray,
    onTertiary = DarkGray,
    background = LightGray
)

private var statusBar: Color by mutableStateOf(lightColorScheme.background)


@Composable
fun SimplePlannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        LaunchedEffect(statusBar) {
            val window = (view.context as Activity).window
            window.statusBarColor = statusBar.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme.not()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

fun setStatusBarColor(color: Color) {
    statusBar = color
}

fun getColorScheme() = lightColorScheme