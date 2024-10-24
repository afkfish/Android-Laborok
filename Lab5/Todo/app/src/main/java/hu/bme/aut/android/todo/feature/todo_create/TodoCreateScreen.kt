package hu.bme.aut.android.todo.feature.todo_create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.todo.R
import hu.bme.aut.android.todo.domain.model.Priority
import hu.bme.aut.android.todo.ui.common.TodoAppBar
import hu.bme.aut.android.todo.ui.common.TodoEditor
import hu.bme.aut.android.todo.ui.model.asPriorityUi
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import java.time.Instant
import java.time.ZoneId

// JOYAXJ
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCreateScreen(
    onNavigateBack: () -> Unit,
    viewModel: TodoCreateViewModel = viewModel(factory = TodoCreateViewModel.Factory)
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val hostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when(uiEvent) {
                is TodoCreateUiEvent.Success -> { onNavigateBack() }
                is TodoCreateUiEvent.Failure -> {
                    scope.launch {
                        hostState.showSnackbar(uiEvent.error.asString(context))
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
        topBar = {
            TodoAppBar(
                title = stringResource(id = R.string.app_bar_title_create_todo),
                onNavigateBack = onNavigateBack,
                actions = { }
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { viewModel.onEvent(TodoCreateEvent.SaveTodo) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            TodoEditor(
                titleValue = state.todo.title,
                titleOnValueChange = { viewModel.onEvent(TodoCreateEvent.ChangeTitle(it)) },
                descriptionValue = state.todo.description,
                descriptionOnValueChange = { viewModel.onEvent(TodoCreateEvent.ChangeDescription(it)) },
                priorities = Priority.entries.map { it.asPriorityUi() },
                selectedPriority = state.todo.priority,
                onPrioritySelected = { viewModel.onEvent(TodoCreateEvent.SelectPriority(it)) },
                pickedDate = state.todo.dueDate.toLocalDate(),
                onDateSelectorClicked = {
                    showDialog = true
                },
                modifier = Modifier
            )
        }
        if (showDialog) {
            Box() {
                // JOYAXJ
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
                                    TodoCreateEvent.SelectDate(
                                        LocalDate(date.year, date.month, date.dayOfMonth)
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