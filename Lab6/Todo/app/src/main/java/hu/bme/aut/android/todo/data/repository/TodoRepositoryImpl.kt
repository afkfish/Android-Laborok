package hu.bme.aut.android.todo.data.repository

import hu.bme.aut.android.todo.data.dao.TodoDao
import hu.bme.aut.android.todo.data.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val dao: TodoDao) : TodoRepository {

    override fun getAllTodos(): Flow<List<TodoEntity>> = dao.getAllTodos()

    override fun getTodoById(id: Int): Flow<TodoEntity> = dao.getTodoById(id)

    override suspend fun insertTodo(todo: TodoEntity) { dao.insertTodo(todo) }

    override suspend fun updateTodo(todo: TodoEntity) { dao.updateTodo(todo) }

    override suspend fun deleteTodo(id: Int) { dao.deleteTodo(id) }

    override suspend fun deleteAllTodos() { dao.deleteAllTodos() }
}