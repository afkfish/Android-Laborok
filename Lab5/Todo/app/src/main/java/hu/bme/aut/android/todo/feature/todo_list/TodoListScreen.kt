package hu.bme.aut.android.todo.feature.todo_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.todo.R
import hu.bme.aut.android.todo.ui.model.toUiText
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

// JOYAXJ
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoListScreen(
    onListItemClick: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: TodoListViewModel = viewModel(factory = TodoListViewModel.Factory),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadTodos()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            Column {
                LargeFloatingActionButton(
                    onClick = { viewModel.shuffleTodos() },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(imageVector = Icons.Default.Shuffle, contentDescription = null)
                }
                LargeFloatingActionButton(
                    onClick = onFabClick,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
                .background(
                    color = if (state is TodoListState.Loading || state is TodoListState.Error) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else {
                        MaterialTheme.colorScheme.background
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is TodoListState.Loading -> CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )

                is TodoListState.Error -> Text(
                    text = state.error.toUiText().asString(context)
                )

                is TodoListState.Result -> {
                    if (state.todoList.isEmpty()) {
                        Text(text = stringResource(id = R.string.text_empty_todo_list))
                    } else {
                        Column {
                            Text(
                                text = stringResource(id = R.string.text_your_todo_list),
                                fontSize = 24.sp
                            )
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                items(state.todoList, key = { todo -> todo.id }) { todo ->
                                    ListItem(
                                        leadingContent = {
                                            Icon(
                                                imageVector = Icons.Default.Circle,
                                                contentDescription = null,
                                                tint = todo.priority.color,
                                                modifier = Modifier
                                                    .size(64.dp)
                                            )
                                        },
                                        headlineContent = {
                                            Text(text = todo.title)
                                        },
                                        supportingContent = {
                                            Text(
                                                text = stringResource(
                                                    id = R.string.list_item_supporting_text,
                                                    todo.dueDate
                                                )
                                            )
                                        },
                                        // JOYAXJ
                                        modifier = Modifier.clickable(onClick = {
                                            onListItemClick(
                                                todo.id
                                            )
                                        })
                                            .animateItem()
                                    )
                                    if (state.todoList.last() != todo) {
                                        HorizontalDivider(
                                            thickness = 2.dp,
                                            color = MaterialTheme.colorScheme.secondaryContainer
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}