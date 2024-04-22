package com.swirl.listifyplanner.db

import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun toDate(dateString: String?): LocalDateTime {
        return dateString.let {
            runCatching { LocalDateTime.parse(dateString) }.getOrNull()
        } ?: LocalDateTime.now()
    }

    @TypeConverter
    fun toDateString(date: LocalDateTime?): String {
        return date?.toString() ?: LocalDateTime.now().toString()
    }
}