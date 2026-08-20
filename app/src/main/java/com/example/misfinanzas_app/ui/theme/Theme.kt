package com.example.misfinanzas_app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = TealPrimaryLight,
    onPrimary = OnTealPrimaryLight,
    primaryContainer = TealPrimaryContainerLight,
    onPrimaryContainer = OnTealPrimaryContainerLight,
    secondary = BlueSecondaryLight,
    onSecondary = OnBlueSecondaryLight,
    secondaryContainer = BlueSecondaryContainerLight,
    onSecondaryContainer = OnBlueSecondaryContainerLight,
    tertiary = GreenTertiaryLight,
    onTertiary = OnGreenTertiaryLight,
    tertiaryContainer = GreenTertiaryContainerLight,
    onTertiaryContainer = OnGreenTertiaryContainerLight,
    error = RedErrorLight,
    onError = OnRedErrorLight,
    errorContainer = RedErrorContainerLight,
    onErrorContainer = OnRedErrorContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight
)

private val DarkColorScheme = darkColorScheme(
    primary = TealPrimaryDark,
    onPrimary = OnTealPrimaryDark,
    primaryContainer = TealPrimaryContainerDark,
    onPrimaryContainer = OnTealPrimaryContainerDark,
    secondary = BlueSecondaryDark,
    onSecondary = OnBlueSecondaryDark,
    secondaryContainer = BlueSecondaryContainerDark,
    onSecondaryContainer = OnBlueSecondaryContainerDark,
    tertiary = GreenTertiaryDark,
    onTertiary = OnGreenTertiaryDark,
    tertiaryContainer = GreenTertiaryContainerDark,
    onTertiaryContainer = OnGreenTertiaryContainerDark,
    error = RedErrorDark,
    onError = OnRedErrorDark,
    errorContainer = RedErrorContainerDark,
    onErrorContainer = OnRedErrorContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark
)

@Composable
fun MisFinanzasAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = FinanzasShapes,
        content = content
    )
}
