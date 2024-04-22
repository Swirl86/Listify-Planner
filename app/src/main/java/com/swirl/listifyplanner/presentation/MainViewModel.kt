package com.swirl.listifyplanner.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swirl.listifyplanner.data.model.Todo
import com.swirl.listifyplanner.data.repository.TodoRepository
import com.swirl.listifyplanner.utils.extenstions.capitalizeWord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    var todo by mutableStateOf(Todo(0, "", LocalDateTime.now()))
        private set

    val getAllTodos: Flow<List<Todo>> = todoRepository.getAllTodos()

    private var deletedTodo: Todo? = null

    fun insertTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.insertTodo(
                todo.copy(
                    task = todo.task.capitalizeWord(),
                    timeStamp = LocalDateTime.now()
                )
            )
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.updateTodo(
                todo.copy(
                    task = todo.task.capitalizeWord(),
                    timeStamp = LocalDateTime.now()
                )
            )
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            deletedTodo = todo
            todoRepository.deleteTodo(todo)
        }
    }

    fun deleteAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.deleteAllTodos()
        }
    }

    fun undoDeletedTodo()  {
        deletedTodo?.let { todo ->
            insertTodo(todo)
        }
    }

    fun getTodoById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todo = todoRepository.getTodoById(id)
        }
    }

    fun updateTask(newValue: String) {
        todo = todo.copy(task = newValue)
    }

    fun updateIsImportant(newValue: Boolean) {
        todo = todo.copy(isImportant = newValue)
    }
}