package com.swirl.listifyplanner.data.model

import androidx.compose.ui.graphics.Color
import java.io.Serializable
import java.time.LocalDateTime

data class Note(
    val title: String,
    val description: String,
    val color: Color,
    val timeStamp: LocalDateTime
): Serializable
