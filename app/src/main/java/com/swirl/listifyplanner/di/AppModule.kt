package com.swirl.listifyplanner.di

import com.swirl.listifyplanner.data.repository.CalendarNoteRepository
import com.swirl.listifyplanner.data.repository.TodoRepository
import com.swirl.listifyplanner.db.AppDatabase
import com.swirl.listifyplanner.db.dao.CalendarNoteDao
import com.swirl.listifyplanner.db.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDao(db: AppDatabase): TodoDao = db.todoDao()

    @Provides
    @Singleton
    fun provideTodoRepository(todoDao: TodoDao) = TodoRepository(todoDao = todoDao)

    @Provides
    @Singleton
    fun provideCalendarNoteDao(db: AppDatabase): CalendarNoteDao = db.calendarNoteDao()

    @Provides
    @Singleton
    fun provideCalendarNoteRepository(calendarNoteDao: CalendarNoteDao) = CalendarNoteRepository(cnDao = calendarNoteDao)

}