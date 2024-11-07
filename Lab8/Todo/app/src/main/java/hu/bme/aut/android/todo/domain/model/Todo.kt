package hu.bme.aut.android.todo.domain.model

import hu.bme.aut.android.todo.data.entities.TodoEntity
import kotlinx.datetime.LocalDate

data class Todo(
    val id: Int,
    val title: String,
    val priority: Priority,
    val dueDate: LocalDate,
    val description: String
)

fun TodoEntity.asTodo(): Todo = Todo(
    id = id,
    title = title,
    priority = priority,
    dueDate = dueDate,
    description = description
)

fun Todo.asTodoEntity(): TodoEntity = TodoEntity(
    id = id,
    title = title,
    priority = priority,
    dueDate = dueDate,
    description = description
)