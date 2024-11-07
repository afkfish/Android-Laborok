package hu.bme.aut.android.todo.feature.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.todo.TodoApplication
import hu.bme.aut.android.todo.data.datasource.TodoRepository
import hu.bme.aut.android.todo.domain.usecases.TodoUseCases
import hu.bme.aut.android.todo.ui.model.TodoUi
import hu.bme.aut.android.todo.ui.model.asTodoUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// JOYAXJ
@HiltViewModel
class TodosViewModel @Inject constructor(
    private val todoOperations: TodoUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(TodosState())
    val state = _state.asStateFlow()

    init {
        loadTodos()
    }
    private fun loadTodos() {

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    val todos = todoOperations.loadTodos().getOrThrow().map { it.asTodoUi() }
                    _state.update { it.copy(
                        isLoading = false,
                        todos = todos
                    ) }
                }
            } catch (e: Exception) {
                _state.update {  it.copy(
                    isLoading = false,
                    error = e
                ) }
            }
        }
    }
}

data class TodosState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val isError: Boolean = error != null,
    val todos: List<TodoUi> = emptyList()
)
