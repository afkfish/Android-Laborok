package hu.bme.aut.android.todo

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.todo.data.TodoDatabase
import hu.bme.aut.android.todo.data.repository.TodoRepositoryImpl

class TodoApplication : Application() {

    companion object {
        private lateinit var db: TodoDatabase

        lateinit var repository: TodoRepositoryImpl
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            "todo_database"
        ).fallbackToDestructiveMigration().build()

        repository = TodoRepositoryImpl(db.dao)
    }
}