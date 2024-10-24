package hu.bme.aut.android.todo.domain.model

import kotlinx.datetime.LocalDate

data class Todo(
    val id: Int,
    val title: String,
    val priority: Priority,
    val dueDate: LocalDate,
    val description: String
)