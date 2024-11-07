package hu.bme.aut.android.todo.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.todo.data.TodoDatabase
import hu.bme.aut.android.todo.data.dao.TodoDao
import javax.inject.Singleton

// JOYAXJ
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseInstance(
        @ApplicationContext context: Context
    ): TodoDatabase = Room.databaseBuilder(
        context,
        TodoDatabase::class.java,
        "todo_database"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideTodoDao(
        db: TodoDatabase
    ): TodoDao = db.dao
}