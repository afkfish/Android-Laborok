package hu.bme.aut.android.todo.domain.model

enum class Priority {
    NONE,
    LOW,
    MEDIUM,
    HIGH;

    companion object {
        val priorities = listOf(NONE, LOW, MEDIUM, HIGH)
    }
}