package hu.bme.aut.android.todo.domain.usecases

import hu.bme.aut.android.todo.data.datasource.TodoRepository
import hu.bme.aut.android.todo.domain.model.Todo
import hu.bme.aut.android.todo.domain.model.asTodo
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