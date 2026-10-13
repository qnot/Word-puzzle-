package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val QuestDarkColorScheme = darkColorScheme(
    primary = QuestIndigoBright,
    onPrimary = Color.White,
    primaryContainer = QuestIndigoMedium,
    onPrimaryContainer = Color(0xFFE0E7FF),
    secondary = QuestAmber,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF78350F),
    onSecondaryContainer = QuestGold,
    tertiary = QuestEmerald,
    onTertiary = Color.White,
    background = QuestIndigoDark,
    onBackground = Color.White,
    surface = DarkSurface,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF3730A3),
    onSurfaceVariant = Color(0xFFC7D2FE)
)

private val QuestLightColorScheme = lightColorScheme(
    primary = QuestIndigoBright,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0E7FF),
    onPrimaryContainer = QuestIndigoDark,
    secondary = QuestAmber,
    onSecondary = Color.Black,
    tertiary = QuestEmerald,
    background = Color(0xFFF8FAFC),
    onBackground = Color(0xFF0F172A),
    surface = Color.White,
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFF475569)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Adventure dark cosmic palette is default
    dynamicColor: Boolean = false, // Keep signature cohesive adventure branding
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) QuestDarkColorScheme else QuestLightColorScheme
    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
