package hu.bme.aut.android.todo.navigation

sealed class Screen(val route: String) {
    object Todos: Screen("todos")
    object CreateTodo: Screen("create")
    object CheckTodo: Screen("check/{id}") {
        fun passId(id: Int) = "check/$id"
    }
}
