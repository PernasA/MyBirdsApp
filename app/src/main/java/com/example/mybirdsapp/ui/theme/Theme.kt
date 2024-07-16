package com.example.mybirdsapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

//el primary no se usa
//el secondary no se usa
//el terciary es para el appbar
//el primaryContainer es para el fondo de la card
//el secondaryContainer es para los botones del HomePage

private val DarkColorScheme = darkColorScheme(
    primary = MossGreenPrimary,
    secondary = MossGreenSecondary,
    tertiary = MossGreenRealTertiary,
    primaryContainer = MossGreenPrimaryContainer,
    secondaryContainer = MossGreenPrimary2,
    onSurfaceVariant = Color.Black,
)

private val MoreMossGreenColorScheme = darkColorScheme(
    primary = MossGreenPrimary2,
    secondary = MossGreenSecondary2,
    tertiary = MossGreenTertiary2,
    primaryContainer = MossGreenPrimaryContainer2,
    secondaryContainer = MossGreenPrimaryContainer2,
    onSurfaceVariant = Color.Black,
)

private val LightColorScheme = lightColorScheme(
    primary = MossGreenPrimaryLight,
    secondary = MossGreenSecondaryLight,
    tertiary = MossGreenTertiaryLight,
    primaryContainer = MossGreenPrimaryContainerLight,
    secondaryContainer = MossGreenTertiaryLight
)

@Composable
fun MyBirdsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme//MoreMossGreenColorScheme
    else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}