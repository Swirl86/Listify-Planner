package com.swirl.listifyplanner.utils

import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

@Composable
fun getPurpleThemeTextFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = Color.Black,
    backgroundColor = Color.LightGray,
    focusedIndicatorColor = Color.Magenta,
    unfocusedIndicatorColor = Color.Gray,
    cursorColor = Color.Magenta
)

/**
 * Function to determine text color based on background color
 * by calculate brightness based on RGB values
 *
 * [Result] If the background color is light returns Black, otherwise White
 */
fun getTextColor(backgroundColor: Color): Color {
    val brightness = (0.299 * backgroundColor.red + 0.587 * backgroundColor.green + 0.114 * backgroundColor.blue)
    return if (brightness > 0.5) Color.Black else Color.White
}

/**
 * Use for Image colorFilter
 *
 * If true return sent color slight tinted else return color as is
 */
fun getImageColor(tint: Boolean, color: Color) =
    if (tint) ColorFilter.tint(color.copy(alpha = 0.5f)) else ColorFilter.tint(color)

/**
 * Use for Icon tint
 *
 * If true return sent color slight tinted else return color as is
 */
fun getIconColor(tint: Boolean, color: Color) =
    if (tint) color.copy(alpha = 0.5f) else color