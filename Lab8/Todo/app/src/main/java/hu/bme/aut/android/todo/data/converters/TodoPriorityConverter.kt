package hu.bme.aut.android.todo.data.converters

import androidx.room.TypeConverter
import hu.bme.aut.android.todo.domain.model.Priority

object TodoPriorityConverter {

    @TypeConverter
    fun Priority.asString(): String = this.name

    @TypeConverter
    fun String.asPriority(): Priority {
        return when(this) {
            Priority.LOW.name -> Priority.LOW
            Priority.MEDIUM.name -> Priority.MEDIUM
            Priority.HIGH.name -> Priority.HIGH
            else -> Priority.LOW
        }
    }

}