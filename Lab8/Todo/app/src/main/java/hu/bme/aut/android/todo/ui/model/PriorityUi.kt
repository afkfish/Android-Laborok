package hu.bme.aut.android.todo.ui.model

import androidx.compose.ui.graphics.Color
import hu.bme.aut.android.todo.R
import hu.bme.aut.android.todo.domain.model.Priority

sealed class PriorityUi(
    val title: Int,
    val color: Color
) {
    object None: PriorityUi(
        title =  R.string.priority_title_none,
        color = Color(0xFFE6E4E4)
    )
    object Low: PriorityUi(
        title = R.string.priority_title_low,
        color = Color(0xFF8BC34A)
    )
    object Medium: PriorityUi(
        title = R.string.priority_title_medium,
        color = Color(0xFFFFC107)
    )
    object High: PriorityUi(
        title = R.string.priority_title_high,
        color = Color(0xFFF44336)
    )
}

fun PriorityUi.asPriority(): Priority {
    return when(this) {
        is PriorityUi.None -> Priority.NONE
        is PriorityUi.Low -> Priority.LOW
        is PriorityUi.Medium -> Priority.MEDIUM
        is PriorityUi.High -> Priority.HIGH
    }
}

fun Priority.asPriorityUi(): PriorityUi {
    return when(this) {
        Priority.NONE -> hu.bme.aut.android.todo.ui.model.PriorityUi.None
        Priority.LOW -> hu.bme.aut.android.todo.ui.model.PriorityUi.Low
        Priority.MEDIUM -> hu.bme.aut.android.todo.ui.model.PriorityUi.Medium
        Priority.HIGH -> hu.bme.aut.android.todo.ui.model.PriorityUi.High
    }
}
