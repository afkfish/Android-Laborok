package hu.bme.aut.android.todo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.bme.aut.android.todo.domain.model.Priority
import kotlinx.datetime.LocalDate

@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val priority: Priority,
    val dueDate: LocalDate,
    val description: String
)