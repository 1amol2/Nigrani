package com.dev.nigrani.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val NigraniLightColorScheme = lightColorScheme(
    primary = Color(0xFF174A7E),
    onPrimary = Color.White,

    primaryContainer = Color(0xFFDCEBFA),
    onPrimaryContainer = Color(0xFF0D355C),

    secondary = Color(0xFF2563A6),
    onSecondary = Color.White,

    secondaryContainer = Color(0xFFE2EFFB),
    onSecondaryContainer = Color(0xFF123A5D),

    tertiary = Color(0xFF3B6E9E),
    onTertiary = Color.White,

    background = Color(0xFFF7F9FC),
    onBackground = Color(0xFF172033),

    surface = Color.White,
    onSurface = Color(0xFF172033),

    surfaceVariant = Color(0xFFEEF2F7),
    onSurfaceVariant = Color(0xFF64748B),

    outline = Color(0xFFD7DEE8),
    outlineVariant = Color(0xFFE5EAF0),

    error = Color(0xFFD92D20),
    onError = Color.White,

    errorContainer = Color(0xFFFEE4E2),
    onErrorContainer = Color(0xFF7A271A)
)

private val NigraniDarkColorScheme = darkColorScheme(
    primary = Color(0xFF7DB5E8),
    onPrimary = Color(0xFF06345C),

    primaryContainer = Color(0xFF0D355C),
    onPrimaryContainer = Color(0xFFDCEBFA),

    secondary = Color(0xFF8FC5F5),
    onSecondary = Color(0xFF073557),

    secondaryContainer = Color(0xFF16476D),
    onSecondaryContainer = Color(0xFFE2EFFB),

    tertiary = Color(0xFF9CC7ED),
    onTertiary = Color(0xFF123A5D),

    background = Color(0xFF101820),
    onBackground = Color(0xFFE8EEF5),

    surface = Color(0xFF17232E),
    onSurface = Color(0xFFE8EEF5),

    surfaceVariant = Color(0xFF253442),
    onSurfaceVariant = Color(0xFFB8C5D2),

    outline = Color(0xFF526473),
    outlineVariant = Color(0xFF3A4A59),

    error = Color(0xFFFF6B60),
    onError = Color(0xFF5C0000),

    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFFFDAD6)
)

@Composable
fun NigraniTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        NigraniDarkColorScheme
    } else {
        NigraniLightColorScheme
    }

    // Match Android system bars with the Nigrani theme.
    val view = LocalView.current

    if (!view.isInEditMode) {
        val window = (view.context as Activity).window

        window.statusBarColor = colorScheme.background.toArgb()
        window.navigationBarColor = colorScheme.background.toArgb()

        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = !darkTheme
            isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}