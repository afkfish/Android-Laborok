package hu.bme.aut.android.todo.domain.usecases

import hu.bme.aut.android.todo.data.datasource.TodoRepository


class DeleteTodoUseCase(private val repository: TodoRepository) {

    suspend operator fun invoke(id: Int) {
        repository.deleteTodo(id)
    }

}