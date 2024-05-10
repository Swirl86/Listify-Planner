package com.swirl.listifyplanner.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.swirl.listifyplanner.data.repository.CalendarNoteRepository
import com.swirl.listifyplanner.data.repository.TodoRepository
import com.swirl.listifyplanner.db.AppDatabase
import com.swirl.listifyplanner.db.dao.CalendarNoteDao
import com.swirl.listifyplanner.db.dao.TodoDao
import com.swirl.listifyplanner.presentation.MainViewModel

class MainViewModelFactory(
    private val todoRepository: TodoRepository,
    private val calendarNoteRepository: CalendarNoteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(todoRepository, calendarNoteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun ProvideMainViewModelForPreview(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val database =  AppDatabase.getInstance(context)
    val todoDao: TodoDao = database.todoDao()
    val calendarNoteDao: CalendarNoteDao = database.calendarNoteDao()

    val todoRepository = TodoRepository(todoDao)
    val calendarNoteRepository = CalendarNoteRepository(calendarNoteDao)

    val mainViewModelFactory = MainViewModelFactory(todoRepository, calendarNoteRepository)
    val mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory)

    content()
}