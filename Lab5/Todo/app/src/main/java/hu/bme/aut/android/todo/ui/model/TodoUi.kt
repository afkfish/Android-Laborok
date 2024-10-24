package hu.bme.aut.android.todo.ui.model

import hu.bme.aut.android.todo.domain.model.Todo
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import java.time.LocalDateTime

data class TodoUi(
    val id: Int = 0,
    val title: String = "",
    val priority: PriorityUi = PriorityUi.None,
    val dueDate: String = LocalDate(
        LocalDateTime.now().year,
        LocalDateTime.now().monthValue,
        LocalDateTime.now().dayOfMonth
    ).toString(),
    val description: String = ""
)

fun Todo.asTodoUi(): TodoUi = TodoUi(
    id = id,
    title = title,
    priority = priority.asPriorityUi(),
    dueDate = dueDate.toString(),
    description = description
)

fun TodoUi.asTodo(): Todo = Todo(
    id = id,
    title = title,
    priority = priority.asPriority(),
    dueDate = dueDate.toLocalDate(),
    description = description
)