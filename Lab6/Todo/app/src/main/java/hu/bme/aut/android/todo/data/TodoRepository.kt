package hu.bme.aut.android.todo.data

import hu.bme.aut.android.todo.domain.model.Todo

interface TodoRepository {
    suspend fun insertTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
    suspend fun getTodoById(id: Int): Todo
    suspend fun getAllTodos(): List<Todo>
    suspend fun updateTodo(updatedTodo: Todo)
}