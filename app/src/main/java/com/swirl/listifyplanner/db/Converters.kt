package com.swirl.listifyplanner.db

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun toDateTime(dateString: String?): LocalDateTime {
        return dateString.let {
            runCatching { LocalDateTime.parse(dateString) }.getOrNull()
        } ?: LocalDateTime.now()
    }

    @TypeConverter
    fun toDateTimeString(date: LocalDateTime?): String {
        return date?.toString() ?: LocalDateTime.now().toString()
    }

    @TypeConverter
    fun toDate(dateString: String?): LocalDate {
        return dateString.let {
            runCatching { LocalDate.parse(dateString) }.getOrNull()
        } ?: LocalDate.now()
    }

    @TypeConverter
    fun toDateString(date: LocalDate?): String {
        return date?.toString() ?: LocalDate.now().toString()
    }

    @TypeConverter
    fun toLong(color: Color): Long = color.value.toLong()

    @TypeConverter
    fun toColor(value: Long): Color = Color(value)
}