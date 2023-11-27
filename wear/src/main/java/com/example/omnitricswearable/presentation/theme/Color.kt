package com.example.omnitricswearable.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Red400 = Color(0xFFCF6679)

//Coolors
val White = Color(0xffffffff)
val YaleBlue = Color (0xff084887)

val BlackChocolate = Color(0xff141301)
val Flame = Color(0xffe53d00)
val DeepSaffron = Color(0xffff9b42)

//Coolors for Status
val ShamrockGreen = Color(0xff109648) // Finished Status
val DimGray = Color(0xff6b6570) // Stopped Status
val CarolinaBlue = Color(0xff1b98e0) // Playing Status
val LemonChiffon = Color(0xfffef6c9) // Paused Status

internal val wearColorPalette: Colors = Colors(
    primary = YaleBlue,
    primaryVariant = CarolinaBlue,
    secondary = LemonChiffon,
    secondaryVariant = LemonChiffon,
    error = Flame,
    onPrimary = White,
    onSecondary = BlackChocolate,
    onError = White
)

//internal val wearColorPalette: Colors = Colors(
//    primary = Purple200,
//    primaryVariant = Purple700,
//    secondary = Teal200,
//    secondaryVariant = Teal200,
//    error = Red400,
//    onPrimary = Color.Black,
//    onSecondary = Color.Black,
//    onError = Color.Black
//)