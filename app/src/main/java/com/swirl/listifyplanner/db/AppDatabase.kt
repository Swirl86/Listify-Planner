package com.swirl.listifyplanner.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "local_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}