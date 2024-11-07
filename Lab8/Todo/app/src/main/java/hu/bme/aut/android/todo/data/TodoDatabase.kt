package hu.bme.aut.android.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.android.todo.data.converters.LocalDateConverter
import hu.bme.aut.android.todo.data.converters.TodoPriorityConverter
import hu.bme.aut.android.todo.data.dao.TodoDao
import hu.bme.aut.android.todo.data.entities.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
@TypeConverters(TodoPriorityConverter::class, LocalDateConverter::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract val dao: TodoDao
}