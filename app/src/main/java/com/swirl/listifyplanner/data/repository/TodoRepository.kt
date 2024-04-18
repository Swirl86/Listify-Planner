package com.swirl.listifyplanner.data.repository

import com.swirl.listifyplanner.data.model.Todo
import com.swirl.listifyplanner.db.dao.TodoDao
import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val todoDao: TodoDao
) {
    suspend fun insertTodo(todo: Todo) = todoDao.insertTodo(todo)
    suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(todo)
    suspend fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo)
    suspend fun deleteAllTodos() = todoDao.deleteAllTodos()
    suspend fun getTodoById(id: Int): Todo = todoDao.getTodoById(id)
    fun getAllTodos(): Flow<List<Todo>> = todoDao.getAllTodos()
}