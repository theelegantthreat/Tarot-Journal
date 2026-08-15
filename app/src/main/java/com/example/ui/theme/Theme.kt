package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = StarlightGold,
    onPrimary = ObsidianDeep,
    primaryContainer = ObsidianCard,
    onPrimaryContainer = StarlightGold,
    secondary = AmethystPurple,
    onSecondary = ObsidianDeep,
    secondaryContainer = ObsidianSurfaceVariant,
    onSecondaryContainer = StarlightWhite,
    tertiary = CelestialCyan,
    onTertiary = ObsidianDeep,
    background = ObsidianDeep,
    onBackground = StarlightWhite,
    surface = ObsidianSurface,
    onSurface = StarlightWhite,
    surfaceVariant = ObsidianSurfaceVariant,
    onSurfaceVariant = StarlightMuted,
    outline = ObsidianBorder,
    outlineVariant = ObsidianBorder.copy(alpha = 0.5f)
)

private val LightColorScheme = darkColorScheme(
    primary = StarlightGold,
    onPrimary = ObsidianDeep,
    primaryContainer = ObsidianCard,
    onPrimaryContainer = StarlightGold,
    secondary = AmethystPurple,
    onSecondary = ObsidianDeep,
    secondaryContainer = ObsidianSurfaceVariant,
    onSecondaryContainer = StarlightWhite,
    tertiary = CelestialCyan,
    onTertiary = ObsidianDeep,
    background = ObsidianDeep,
    onBackground = StarlightWhite,
    surface = ObsidianSurface,
    onSurface = StarlightWhite,
    surfaceVariant = ObsidianSurfaceVariant,
    onSurfaceVariant = StarlightMuted,
    outline = ObsidianBorder,
    outlineVariant = ObsidianBorder.copy(alpha = 0.5f)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // We enforce our signature Celestial Obsidian & Gold theme
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
