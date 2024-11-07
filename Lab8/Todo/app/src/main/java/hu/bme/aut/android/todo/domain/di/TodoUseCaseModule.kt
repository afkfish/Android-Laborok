package hu.bme.aut.android.todo.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.todo.data.datasource.TodoRepository
import hu.bme.aut.android.todo.domain.usecases.DeleteTodoUseCase
import hu.bme.aut.android.todo.domain.usecases.LoadTodoUseCase
import hu.bme.aut.android.todo.domain.usecases.LoadTodosUseCase
import hu.bme.aut.android.todo.domain.usecases.SaveTodoUseCase
import hu.bme.aut.android.todo.domain.usecases.TodoUseCases
import hu.bme.aut.android.todo.domain.usecases.UpdateTodoUseCase
import javax.inject.Singleton

// JOYAXJ
@Module
@InstallIn(SingletonComponent::class)
object TodoUseCaseModule {

    @Provides
    @Singleton
    fun provideLoadTodosUseCase(
        repository: TodoRepository
    ): LoadTodosUseCase = LoadTodosUseCase(repository)

    @Provides
    @Singleton
    fun provideLoadTodoUseCase(
        repository: TodoRepository
    ): LoadTodoUseCase = LoadTodoUseCase(repository)

    @Provides
    @Singleton
    fun provideSaveTodoUseCase(
        repository: TodoRepository
    ): SaveTodoUseCase = SaveTodoUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateTodoUseCase(
        repository: TodoRepository
    ): UpdateTodoUseCase = UpdateTodoUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteTodoUseCase(
        repository: TodoRepository
    ): DeleteTodoUseCase = DeleteTodoUseCase(repository)

    @Provides
    @Singleton
    fun provideTodoUseCases(
        repository: TodoRepository,
        loadTodos: LoadTodosUseCase,
        loadTodo: LoadTodoUseCase,
        saveTodo: SaveTodoUseCase,
        updateTodo: UpdateTodoUseCase,
        deleteTodo: DeleteTodoUseCase
    ): TodoUseCases = TodoUseCases(repository, loadTodos, loadTodo, saveTodo, updateTodo, deleteTodo)

}