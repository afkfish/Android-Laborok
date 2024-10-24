package hu.bme.aut.android.todo.domain.usecases

import hu.bme.aut.android.todo.data.repository.TodoRepository

class DeleteTodosUseCase(private val repository: TodoRepository) {

    suspend operator fun invoke() {
        repository.deleteAllTodos()
    }

}