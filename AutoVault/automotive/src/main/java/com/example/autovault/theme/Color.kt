package com.example.autovault.theme

import androidx.compose.ui.graphics.Color

val PastelGreen = Color(0xFFB8E4C9)
val PastelRed = Color(0xFFFFBABA)
val PastelBlue = Color(0xFFB3D9FF)
val PastelYellow = Color(0xFFFFF3B0)
val DarkGray = Color(0xFF2C2C2C)
val LightGray = Color(0xFFEEEEEE)

val LightColorScheme = androidx.compose.material3.lightColorScheme(
    primary = PastelGreen,
    onPrimary = DarkGray,
    secondary = PastelBlue,
    onSecondary = DarkGray,
    background = LightGray,
    onBackground = DarkGray,
    surface = Color.White,
    onSurface = DarkGray,
)

val DarkColorScheme = androidx.compose.material3.darkColorScheme(
    primary = PastelGreen,
    onPrimary = Color.Black,
    secondary = PastelBlue,
    onSecondary = Color.Black,
    background = DarkGray,
    onBackground = Color.White,
    surface = Color(0xFF121212),
    onSurface = Color.White,
)
