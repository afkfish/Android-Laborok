package hu.bme.aut.android.todo.feature.todo_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.todo.data.ITodoRepository
import hu.bme.aut.android.todo.data.MemoryTodoRepository
import hu.bme.aut.android.todo.ui.model.TodoUi
import hu.bme.aut.android.todo.ui.model.asTodoUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class TodoDetailState {
    object Loading : TodoDetailState()
    data class Error(val error: Throwable) : TodoDetailState()
    data class Result(val todo: TodoUi) : TodoDetailState()
}

class TodoDetailViewModel(private val repository: ITodoRepository, private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _state = MutableStateFlow<TodoDetailState>(TodoDetailState.Loading)
    val state = _state.asStateFlow()

    init {
        loadTodos()
    }

    private fun loadTodos() {
        val id = checkNotNull<Int>(savedStateHandle["id"])
        viewModelScope.launch {
            try {
                _state.value = TodoDetailState.Loading
                delay(2000)
                val todo = repository.getTodoById(id)
                _state.value = TodoDetailState.Result(
                    todo.asTodoUi()
                )
            } catch (e: Exception) {
                _state.value = TodoDetailState.Error(e)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                TodoDetailViewModel(
                    MemoryTodoRepository,
                    savedStateHandle
                )
            }
        }
    }
}