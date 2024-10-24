package hu.bme.aut.android.todo.data.repository

import hu.bme.aut.android.todo.data.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getAllTodos(): Flow<List<TodoEntity>>

    fun getTodoById(id: Int): Flow<TodoEntity>

    suspend fun insertTodo(todo: TodoEntity)

    suspend fun updateTodo(todo: TodoEntity)

    suspend fun deleteTodo(id: Int)

    suspend fun deleteAllTodos()
}