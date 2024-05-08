package com.swirl.listifyplanner.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.swirl.listifyplanner.data.model.CalendarNote
import com.swirl.listifyplanner.data.model.Todo
import com.swirl.listifyplanner.db.dao.CalendarNoteDao
import com.swirl.listifyplanner.db.dao.TodoDao

@Database(
    entities = [
        Todo::class,
        CalendarNote::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
    abstract fun calendarNoteDao(): CalendarNoteDao
}