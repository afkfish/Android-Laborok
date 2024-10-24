package hu.bme.aut.android.todo.domain.usecases

import hu.bme.aut.android.todo.TodoApplication.Companion.repository
import hu.bme.aut.android.todo.data.entities.asTodo
import hu.bme.aut.android.todo.data.repository.TodoRepository
import hu.bme.aut.android.todo.domain.model.Todo
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadTodosUseCase(private val repository: TodoRepository) {

    suspend operator fun invoke(): Result<List<Todo>> {
        return try {
            val todos = repository.getAllTodos().first()
            Result.success(todos.map { it.asTodo() })
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}