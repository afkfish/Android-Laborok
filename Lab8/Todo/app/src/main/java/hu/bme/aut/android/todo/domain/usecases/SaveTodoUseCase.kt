package hu.bme.aut.android.todo.domain.usecases

import hu.bme.aut.android.todo.data.datasource.TodoRepository
import hu.bme.aut.android.todo.domain.model.Todo
import hu.bme.aut.android.todo.domain.model.asTodoEntity

class SaveTodoUseCase(private val repository: TodoRepository) {

    suspend operator fun invoke(todo: Todo) {
        repository.insertTodo(todo.asTodoEntity())
    }

}