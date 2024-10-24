package hu.bme.aut.android.todo.data

import hu.bme.aut.android.todo.domain.model.Priority
import hu.bme.aut.android.todo.domain.model.Todo
import kotlinx.coroutines.delay
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime

object MemoryTodoRepository : ITodoRepository {
    private val todos = mutableListOf(
        Todo(
            id = 1,
            title = "Teszt feladat 1",
            priority = Priority.LOW,
            description = "Feladat leírás 1",
            dueDate = LocalDateTime.now().toKotlinLocalDateTime().date,
        ),
        Todo(
            id = 2,
            title = "Teszt feladat 2",
            priority = Priority.MEDIUM,
            description = "Feladat leírás 2",
            dueDate = LocalDateTime.now().toKotlinLocalDateTime().date,
        ),
        Todo(
            id = 3,
            title = "Teszt feladat 3",
            priority = Priority.HIGH,
            description = "Feladat leírás 3",
            dueDate = LocalDateTime.now().toKotlinLocalDateTime().date,
        ),
        Todo(
            id = 4,
            title = "Teszt feladat 4 hosszű szöveg, hogy több sorba kelljen írni",
            priority = Priority.HIGH,
            description = "Feladat leírás 4",
            dueDate = LocalDateTime.now().toKotlinLocalDateTime().date,
        ),
        Todo(
            id = 5,
            title = "Teszt feladat 5",
            priority = Priority.LOW,
            description = "Feladat leírás 5",
            dueDate = LocalDateTime.now().toKotlinLocalDateTime().date,
        ),
        Todo(
            id = 6,
            title = "Teszt feladat 6",
            priority = Priority.MEDIUM,
            description = "Feladat leírás 6",
            dueDate = LocalDateTime.now().toKotlinLocalDateTime().date,
        )
    )

    override suspend fun insertTodo(todo: Todo) {
        delay(1000)
        todos.add(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        delay(1000)
        todos.remove(todo)
    }

    override suspend fun getTodoById(id: Int): Todo {
        delay(1000)
        for (todo in todos) {
            if (todo.id == id) return todo
        }
        return todos.first()
    }

    override suspend fun getAllTodos(): List<Todo> {
        delay(1000)
        return todos.toList()
    }

    override suspend fun updateTodo(updatedTodo: Todo) {
        delay(1000)
        for (todo in todos) {
            if (todo.id == updatedTodo.id)
                todos[todos.indexOf(todo)] = updatedTodo
        }
    }
}