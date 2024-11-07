package hu.bme.aut.android.todo.domain.usecases

import hu.bme.aut.android.todo.data.datasource.TodoRepository

class TodoUseCases(
    val repository: TodoRepository,
    val loadTodos: LoadTodosUseCase,
    val loadTodo: LoadTodoUseCase,
    val saveTodo: SaveTodoUseCase,
    val updateTodo: UpdateTodoUseCase,
    val deleteTodo: DeleteTodoUseCase
)