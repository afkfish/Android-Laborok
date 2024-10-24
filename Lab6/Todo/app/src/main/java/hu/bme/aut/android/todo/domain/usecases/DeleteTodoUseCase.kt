package hu.bme.aut.android.todo.domain.usecases

import hu.bme.aut.android.todo.data.repository.TodoRepository

class DeleteTodoUseCase(private val repository: TodoRepository) {

    suspend operator fun invoke(id: Int) {
        repository.deleteTodo(id)
    }

}