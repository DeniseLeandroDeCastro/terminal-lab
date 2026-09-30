package br.com.denisecastro.cielopaylab.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = CieloBlue,
    onPrimary = Color.White,

    primaryContainer = CieloBlueLight,
    onPrimaryContainer = CieloBlueDark,

    secondary = Success,
    onSecondary = Color.White,

    secondaryContainer = SuccessContainer,
    onSecondaryContainer = OnSuccessContainer,

    error = ErrorRed,
    onError = Color.White,

    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,

    background = AppBackground,
    onBackground = TextPrimary,

    surface = SurfaceWhite,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceSoft,
    onSurfaceVariant = TextSecondary,

    outline = BorderColor
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF003258),

    primaryContainer = Color(0xFF0D47A1),
    onPrimaryContainer = Color(0xFFD6E4FF),

    secondary = Color(0xFF7AD7A2),
    onSecondary = Color(0xFF00391E),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),

    background = Color(0xFF101318),
    onBackground = Color(0xFFE1E7EF),

    surface = Color(0xFF171B21),
    onSurface = Color(0xFFE1E7EF),

    surfaceVariant = Color(0xFF222831),
    onSurfaceVariant = Color(0xFFBEC7D2)
)

@Composable
fun CieloPayLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}