package com.swirl.listifyplanner.utils

import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getPurpleThemeTextFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = Color.Black,
    backgroundColor = Color.LightGray,
    focusedIndicatorColor = Color.Magenta,
    unfocusedIndicatorColor = Color.Gray,
    cursorColor = Color.Magenta
)