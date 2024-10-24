package hu.bme.aut.android.todo.feature.todo_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.todo.ui.common.TodoAppBar
import hu.bme.aut.android.todo.ui.model.toUiText

// JOYAXJ
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: TodoDetailViewModel = viewModel(factory = TodoDetailViewModel.Factory)
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    Scaffold(
        topBar = {
            if (state is TodoDetailState.Result) {
                TodoAppBar(
                    title = state.todo.title,
                    onNavigateBack = onNavigateBack,
                )
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is TodoDetailState.Loading -> CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
                is TodoDetailState.Error -> Text(
                    text = state.error.toUiText().asString(context)
                )
                is TodoDetailState.Result -> {
                    val todo = state.todo
                    Column(
                        modifier = Modifier.fillMaxSize().padding(all = 8.dp)
                    ) {
                        Text(
                            todo.dueDate,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Row(
                            modifier = Modifier
                                .height(TextFieldDefaults.MinHeight)
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(size = 5.dp)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Circle,
                                contentDescription = null,
                                tint = todo.priority.color,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                modifier = Modifier
                                    .weight(weight = 8f),
                                text = stringResource(id = todo.priority.title),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                        Text(
                            todo.description
                        )
                    }
                }
            }
        }
    }
}