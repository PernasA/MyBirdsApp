package com.pernasa.varillasbirdsapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.annotations.TestOnly

// el primary no se usa
// el secondary no se usa
// el terciary es para el appbar
// el primaryContainer es para el fondo de la card
// el secondaryContainer es para los botones del HomePage

@TestOnly
val DarkColorScheme = darkColorScheme(
    primary = MossGreenPrimary,
    secondary = MossGreenSecondary,
    tertiary = MossGreenRealTertiary,
    primaryContainer = MossGreenPrimaryContainer,
    secondaryContainer = MossGreenRealTertiary,
    onSurfaceVariant = Color.Black,
)

@Composable
fun VarillasBirdsAppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}