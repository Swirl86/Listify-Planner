package com.swirl.listifyplanner.di

import com.swirl.listifyplanner.data.repository.CalendarNoteRepository
import com.swirl.listifyplanner.data.repository.TodoRepository
import com.swirl.listifyplanner.presentation.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainViewModelModule {

    @Singleton
    @Provides
    fun provideMainViewModel(
        todoRepository: TodoRepository,
        calendarNoteRepository: CalendarNoteRepository
    ): MainViewModel {
        return MainViewModel(todoRepository, calendarNoteRepository)
    }

}