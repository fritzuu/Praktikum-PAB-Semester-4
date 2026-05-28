package com.praktikumpab.note_manager.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

enum class ThemeMode {
    LIGHT, DARK, AMOLED
}

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = Color.White,
    primaryContainer = DarkPrimaryVariant,
    onPrimaryContainer = DarkOnSurface,
    secondary = DarkSecondary,
    onSecondary = Color.Black,
    tertiary = DarkAccent,
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = DarkOnSurface,
    onSurface = DarkOnSurface,
    outline = DarkOutline,
    error = DarkError
)

private val AmoledColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = Color.White,
    primaryContainer = DarkPrimaryVariant,
    onPrimaryContainer = DarkOnSurface,
    secondary = DarkSecondary,
    onSecondary = Color.Black,
    tertiary = DarkAccent,
    background = Color.Black,
    surface = Color(0xFF121212),
    onBackground = Color.White,
    onSurface = Color.White,
    outline = Color(0xFF222222),
    error = DarkError
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightSurface,
    primaryContainer = LightPrimaryVariant,
    onPrimaryContainer = LightSurface,
    secondary = LightSecondary,
    onSecondary = LightSurface,
    tertiary = LightAccent,
    background = LightBackground,
    surface = LightSurface,
    onBackground = LightOnSurface,
    onSurface = LightOnSurface,
    outline = LightOutline,
    error = LightError
)

@Composable
fun NotemanagerTheme(
    themeMode: ThemeMode = ThemeMode.LIGHT,
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeMode) {
        ThemeMode.LIGHT -> LightColorScheme
        ThemeMode.DARK -> DarkColorScheme
        ThemeMode.AMOLED -> AmoledColorScheme
    }
    
    val isDark = themeMode != ThemeMode.LIGHT
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDark
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

