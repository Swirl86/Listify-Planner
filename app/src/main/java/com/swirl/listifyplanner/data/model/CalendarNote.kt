package com.swirl.listifyplanner.data.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDate

@Entity(tableName = "calendarNotes")
data class CalendarNote(
    @PrimaryKey
    val date: LocalDate,
    val color: Color = Color.Gray,
    val notes: List<Note> = emptyList()
): Serializable