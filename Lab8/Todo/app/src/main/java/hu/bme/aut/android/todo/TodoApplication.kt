package hu.bme.aut.android.todo

import android.app.Application
import androidx.room.Room
import dagger.hilt.android.HiltAndroidApp
import hu.bme.aut.android.todo.data.TodoDatabase
import hu.bme.aut.android.todo.data.dao.TodoDao
import hu.bme.aut.android.todo.data.datasource.TodoRepositoryImpl
import javax.inject.Inject

@HiltAndroidApp
class TodoApplication : Application()