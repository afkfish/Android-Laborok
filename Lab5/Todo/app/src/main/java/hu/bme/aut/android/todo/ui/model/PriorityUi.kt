package hu.bme.aut.android.todo.ui.model

import androidx.compose.ui.graphics.Color
import hu.bme.aut.android.todo.R
import hu.bme.aut.android.todo.domain.model.Priority

enum class PriorityUi(
    val title: Int,
    val color: Color
) {
    None(
        title =  R.string.priority_title_none,
        color = Color(0xFFE6E4E4)
    ),
    Low(
        title = R.string.priority_title_low,
        color = Color(0xFF8BC34A)
    ),
    Medium(
        title = R.string.priority_title_medium,
        color = Color(0xFFFFC107)
    ),
    High(
        title = R.string.priority_title_high,
        color = Color(0xFFF44336)
    ),
}

fun PriorityUi.asPriority(): Priority {
    return when(this) {
        PriorityUi.None -> Priority.NONE
        PriorityUi.Low -> Priority.LOW
        PriorityUi.Medium -> Priority.MEDIUM
        PriorityUi.High -> Priority.HIGH
    }
}

fun Priority.asPriorityUi(): PriorityUi {
    return when(this) {
        Priority.NONE -> PriorityUi.None
        Priority.LOW -> PriorityUi.Low
        Priority.MEDIUM -> PriorityUi.Medium
        Priority.HIGH -> PriorityUi.High
    }
}