package hu.bme.aut.android.todo.feature.todo_check

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.todo.R
import hu.bme.aut.android.todo.ui.common.TodoAppBar
import hu.bme.aut.android.todo.ui.common.TodoEditor
import hu.bme.aut.android.todo.ui.model.TodoUi
import hu.bme.aut.android.todo.ui.util.UiEvent
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import java.time.Instant
import java.time.ZoneId

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun CheckTodoScreen(
    onNavigateBack: () -> Unit,
    viewModel: CheckTodoViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    val hostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Success -> {
                    onNavigateBack()
                }

                is UiEvent.Failure -> {
                    scope.launch {
                        hostState.showSnackbar(uiEvent.message.asString(context))
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
        topBar = {
            if (!state.isLoadingTodo) {
                TodoAppBar(
                    title = if (state.isEditingTodo) {
                        stringResource(id = R.string.app_bar_title_edit_todo)
                    } else state.todo?.title ?: "Todo",
                    onNavigateBack = onNavigateBack,
                    actions = {
                        IconButton(
                            onClick = {
                                if (state.isEditingTodo) {
                                    viewModel.onEvent(CheckTodoEvent.StopEditingTodo)
                                } else {
                                    viewModel.onEvent(CheckTodoEvent.EditingTodo)
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                        }
                        IconButton(
                            onClick = {
                                viewModel.onEvent(CheckTodoEvent.DeleteTodo)
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (state.isEditingTodo) {
                LargeFloatingActionButton(
                    onClick = {
                        viewModel.onEvent(CheckTodoEvent.UpdateTodo)
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoadingTodo) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            } else {
                val todo = state.todo ?: TodoUi()
                TodoEditor(
                    titleValue = todo.title,
                    titleOnValueChange = { viewModel.onEvent(CheckTodoEvent.ChangeTitle(it)) },
                    descriptionValue = todo.description,
                    descriptionOnValueChange = {
                        viewModel.onEvent(
                            CheckTodoEvent.ChangeDescription(
                                it
                            )
                        )
                    },
                    selectedPriority = todo.priority,
                    onPrioritySelected = { viewModel.onEvent(CheckTodoEvent.SelectPriority(it)) },
                    pickedDate = todo.dueDate.toLocalDate(),
                    onDatePickerClicked = {
                        showDialog = true
                    },
                    modifier = Modifier,
                    enabled = state.isEditingTodo
                )
                if (showDialog) {
                    Box() {

                        val datePickerState = rememberDatePickerState()

                        DatePickerDialog(
                            onDismissRequest = {
                                // Dismiss the dialog when the user clicks outside the dialog or on the back
                                // button. If you want to disable that functionality, simply use an empty
                                // onDismissRequest.
                                showDialog = false
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDialog = false
                                        val date =
                                            Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                                                .atZone(ZoneId.systemDefault()).toLocalDateTime()
                                        viewModel.onEvent(
                                            CheckTodoEvent.SelectDate(
                                                LocalDate(
                                                    date.year,
                                                    date.month,
                                                    date.dayOfMonth
                                                )
                                            )
                                        )
                                    }
                                ) {
                                    Text(stringResource(R.string.dialog_ok_button_text))
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        showDialog = false
                                    }
                                ) {
                                    Text(stringResource(R.string.dialog_dismiss_button_text))
                                }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }
                }
            }
        }
    }
}