package com.swirl.listifyplanner.db.dao

import androidx.room.*
import com.swirl.listifyplanner.data.model.CalendarNote
import com.swirl.listifyplanner.data.model.Todo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface CalendarNoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalendarNote(cNote: CalendarNote)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(cNotes: CalendarNote)

    @Delete
    suspend fun delete(cNote: CalendarNote)

    @Query("DELETE FROM calendarNote")
    suspend fun deleteAll()

    @Query("SELECT * FROM calendarNote WHERE date = :id")
    suspend fun getCalendarNoteById(id: LocalDate): CalendarNote

    @Query("SELECT * FROM calendarNote")
    fun getAllCalendarNotes(): Flow<List<CalendarNote>>

}