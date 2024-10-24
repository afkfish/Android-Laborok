package hu.bme.aut.android.todo.domain.usecases

import hu.bme.aut.android.todo.data.repository.TodoRepository

class TodoUseCases(repository: TodoRepository) {
    val loadTodos = LoadTodosUseCase(repository)
    val loadTodo = LoadTodoUseCase(repository)
    val saveTodo = SaveTodoUseCase(repository)
    val updateTodo = UpdateTodoUseCase(repository)
    val deleteTodo = DeleteTodoUseCase(repository)
    val deleteAllTodos = DeleteTodosUseCase(repository)
}