package hu.bme.aut.android.todo.navigation

sealed class Screen(val route: String) {
    object TodoList : Screen("todo_list")
    object TodoDetail : Screen("todo_detail/{id}"){
        fun passId(id: Int) = "todo_detail/$id"
    }
    object TodoCreate : Screen("todo_create")
}