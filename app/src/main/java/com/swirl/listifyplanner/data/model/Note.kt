package com.swirl.listifyplanner.data.model

import androidx.compose.ui.graphics.Color
import androidx.room.PrimaryKey
import java.time.LocalDateTime

data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val color: Color,
    val timeStamp: LocalDateTime
)
