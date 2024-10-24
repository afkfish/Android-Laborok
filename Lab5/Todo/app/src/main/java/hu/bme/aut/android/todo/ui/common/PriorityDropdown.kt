package hu.bme.aut.android.todo.ui.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.todo.ui.model.PriorityUi

@Composable
fun PriorityDropDown(
    priorities: List<PriorityUi>,
    selectedPriority: PriorityUi,
    onPrioritySelected: (PriorityUi) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "Priority arrow angle animation"
    )

    val shape = RoundedCornerShape(5.dp)

    Surface(
        modifier = modifier
            .width(TextFieldDefaults.MinWidth)
            .height(TextFieldDefaults.MinHeight)
            .clip(shape = shape)
            .background(MaterialTheme.colorScheme.background)
            .clickable(enabled = enabled) { expanded = true },
        shape = shape
    ) {
        Row(
            modifier = modifier
                .width(TextFieldDefaults.MinWidth)
                .height(TextFieldDefaults.MinHeight)
                .clip(shape = shape),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                imageVector = Icons.Default.Circle,
                contentDescription = null,
                tint = selectedPriority.color,
                modifier = Modifier
                    .size(20.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                modifier = Modifier
                    .weight(weight = 8f),
                text = stringResource(id = selectedPriority.title),
                style = MaterialTheme.typography.labelMedium
            )
            IconButton(
                modifier = Modifier
                    .weight(weight = 1.5f),
                onClick = { expanded = true }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.padding(5.dp)
                        .graphicsLayer {
                            rotationZ = angle
                        }
                )
            }
            DropdownMenu(
                modifier = modifier
                    .width(TextFieldDefaults.MinWidth),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                priorities.forEach { priority ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(id = priority.title),
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        onClick = {
                            expanded = false
                            onPrioritySelected(priority)
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Circle,
                                contentDescription = null,
                                tint = priority.color,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PriorityDropdown_Preview() {
    val priorities = listOf(PriorityUi.Low, PriorityUi.Medium, PriorityUi.High)
    var selectedPriority by remember { mutableStateOf(priorities[0]) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PriorityDropDown(
            priorities = priorities,
            selectedPriority = selectedPriority,
            onPrioritySelected = {
                selectedPriority = it
            }
        )
    }
}