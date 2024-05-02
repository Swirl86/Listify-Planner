package com.swirl.listifyplanner.data.model

import androidx.compose.ui.graphics.Color
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

data class Note(
    val dateId: LocalDate,
    val title: String,
    val description: String,
    val color: Color,
    val timeStamp: LocalDateTime
): Serializable
