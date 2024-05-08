package com.swirl.listifyplanner.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.swirl.listifyplanner.data.model.CalendarNote
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

    @Query("DELETE FROM calendarNotes")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM calendarNotes WHERE date = :date")
    suspend fun getCalendarNoteByDate(date: LocalDate): CalendarNote?

    @Query("SELECT * FROM calendarNotes")
    fun getAllCalendarNotes(): Flow<List<CalendarNote>>

}