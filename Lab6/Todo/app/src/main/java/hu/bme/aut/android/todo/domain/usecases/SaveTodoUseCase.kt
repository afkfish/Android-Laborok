package hu.bme.aut.android.todo.domain.usecases

import hu.bme.aut.android.todo.data.entities.asTodoEntity
import hu.bme.aut.android.todo.data.repository.TodoRepository
import hu.bme.aut.android.todo.domain.model.Todo

class SaveTodoUseCase(private val repository: TodoRepository) {

    suspend operator fun invoke(todo: Todo) {
        repository.insertTodo(todo.asTodoEntity())
    }

}
