package hu.bme.aut.android.todo.domain.usecases

import hu.bme.aut.android.todo.data.datasource.TodoRepository
import hu.bme.aut.android.todo.domain.model.Todo
import hu.bme.aut.android.todo.domain.model.asTodo
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadTodoUseCase(private val repository: TodoRepository) {

    suspend operator fun invoke(id: Int): Result<Todo> {
        return try {
            Result.success(repository.getTodoById(id).first().asTodo())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}