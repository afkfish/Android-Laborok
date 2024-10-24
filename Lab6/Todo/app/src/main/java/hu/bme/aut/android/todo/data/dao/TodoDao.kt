package hu.bme.aut.android.todo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hu.bme.aut.android.todo.data.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    @Query("SELECT * FROM todo_table")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo_table WHERE id = :id")
    fun getTodoById(id: Int): Flow<TodoEntity>

    @Update
    suspend fun updateTodo(todo: TodoEntity)

    @Query("DELETE FROM todo_table WHERE id = :id")
    suspend fun deleteTodo(id: Int)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTodos()
}