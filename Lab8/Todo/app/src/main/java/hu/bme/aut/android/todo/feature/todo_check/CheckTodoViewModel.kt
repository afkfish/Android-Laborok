package hu.bme.aut.android.todo.feature.todo_check

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.todo.TodoApplication
import hu.bme.aut.android.todo.data.datasource.TodoRepository
import hu.bme.aut.android.todo.domain.usecases.TodoUseCases
import hu.bme.aut.android.todo.ui.model.*
import hu.bme.aut.android.todo.ui.util.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@HiltViewModel
class CheckTodoViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val todoOperations: TodoUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(CheckTodoState())
    val state: StateFlow<CheckTodoState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CheckTodoEvent) {
        when (event) {
            CheckTodoEvent.EditingTodo -> {
                _state.update {
                    it.copy(
                        isEditingTodo = true
                    )
                }
            }

            CheckTodoEvent.StopEditingTodo -> {
                _state.update {
                    it.copy(
                        isEditingTodo = false
                    )
                }
            }

            is CheckTodoEvent.ChangeTitle -> {
                val newValue = event.text
                _state.update {
                    it.copy(
                        todo = it.todo?.copy(title = newValue)
                    )
                }
            }

            is CheckTodoEvent.ChangeDescription -> {
                val newValue = event.text
                _state.update {
                    it.copy(
                        todo = it.todo?.copy(description = newValue)
                    )
                }
            }

            is CheckTodoEvent.SelectPriority -> {
                val newValue = event.priority
                _state.update {
                    it.copy(
                        todo = it.todo?.copy(priority = newValue)
                    )
                }
            }

            is CheckTodoEvent.SelectDate -> {
                val newValue = event.date.toString()
                _state.update {
                    it.copy(
                        todo = it.todo?.copy(dueDate = newValue)
                    )
                }
            }

            CheckTodoEvent.DeleteTodo -> {
                onDelete()
            }

            CheckTodoEvent.UpdateTodo -> {
                onUpdate()
            }
        }
    }

    init {
        load()
    }

    private fun load() {
        val todoId = checkNotNull<Int>(savedState["id"])
        viewModelScope.launch {
            _state.update { it.copy(isLoadingTodo = true) }
            try {
                val todo = todoOperations.loadTodo(todoId)
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    _state.update {
                        it.copy(
                            isLoadingTodo = false,
                            todo = todo.getOrThrow().asTodoUi()
                        )
                    }
                }
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    private fun onUpdate() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                todoOperations.updateTodo(
                    _state.value.todo?.asTodo()!!
                )
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    private fun onDelete() {
        viewModelScope.launch {
            try {
                todoOperations.deleteTodo(state.value.todo!!.id)
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }
}

data class CheckTodoState(
    val todo: TodoUi? = null,
    val isLoadingTodo: Boolean = false,
    val isEditingTodo: Boolean = false,
    val error: Throwable? = null
)

sealed class CheckTodoEvent {
    object EditingTodo: CheckTodoEvent()
    object StopEditingTodo: CheckTodoEvent()
    data class ChangeTitle(val text: String): CheckTodoEvent()
    data class ChangeDescription(val text: String): CheckTodoEvent()
    data class SelectPriority(val priority: PriorityUi): CheckTodoEvent()
    data class SelectDate(val date: LocalDate): CheckTodoEvent()
    object DeleteTodo: CheckTodoEvent()
    object UpdateTodo: CheckTodoEvent()
}


