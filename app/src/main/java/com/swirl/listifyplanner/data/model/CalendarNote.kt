package com.swirl.listifyplanner.data.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "calendarNote")
data class CalendarNote(
    @PrimaryKey
    val date: LocalDate,
    val notes: List<Note> = listOf(),
    val color: Color
)