package com.swirl.listifyplanner.ui.constants

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

fun taskTextStyle(done: Boolean = false) = TextStyle(
    fontSize = 22.sp,
    fontWeight = FontWeight.Medium,
    textDecoration = if (done) TextDecoration.LineThrough else TextDecoration.None
)

val topAppBarTextStyle = TextStyle(
    fontSize = 26.sp,
    fontFamily = FontFamily.Serif
)