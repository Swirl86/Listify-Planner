package com.swirl.listifyplanner.data.repository

import com.swirl.listifyplanner.data.model.CalendarNote
import com.swirl.listifyplanner.db.dao.CalendarNoteDao
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class CalendarNoteRepository(
    private val cnDao: CalendarNoteDao
) {
    suspend fun insertCalendarNote(note: CalendarNote) = cnDao.insertCalendarNote(note)
    suspend fun updateCalendarNote(note: CalendarNote) = cnDao.update(note)
    suspend fun deleteCalendarNote(note: CalendarNote) = cnDao.delete(note)
    suspend fun deleteAllCalendarNotes() = cnDao.deleteAll()
    suspend fun getCalendarNotesByDate(id: LocalDate): CalendarNote = cnDao.getCalendarNoteById(id)
    fun getAllTodos(): Flow<List<CalendarNote>> = cnDao.getAllCalendarNotes()
}