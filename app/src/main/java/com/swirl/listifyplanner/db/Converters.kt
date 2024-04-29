package com.swirl.listifyplanner.db

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.swirl.listifyplanner.data.model.Note
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromTimestamp(dateString: String?): LocalDateTime {
        return dateString.let {
            runCatching { LocalDateTime.parse(dateString) }.getOrNull()
        } ?: LocalDateTime.now()
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String {
        return date?.toString() ?: LocalDateTime.now().toString()
    }

    @TypeConverter
    fun fromDateString(dateString: String?): LocalDate {
        return dateString.let {
            runCatching { LocalDate.parse(dateString) }.getOrNull()
        } ?: LocalDate.now()
    }

    @TypeConverter
    fun dateToString(date: LocalDate?): String {
        return date?.toString() ?: LocalDate.now().toString()
    }

    @TypeConverter
    fun fromColor(color: Color): Long = color.value.toLong()

    @TypeConverter
    fun toColor(value: Long): Color = Color(value)

    @TypeConverter
    fun fromNoteList(notes: List<Note>?): String {
        return gson.toJson(notes)
    }

    @TypeConverter
    fun toNoteList(notesString: String): List<Note> {
        val listType: Type = object : TypeToken<List<Note>>() {}.type
        return gson.fromJson(notesString, listType)
    }
}