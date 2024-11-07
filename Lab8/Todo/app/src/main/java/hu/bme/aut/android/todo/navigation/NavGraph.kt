package hu.bme.aut.android.todo.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.android.todo.feature.todo_check.CheckTodoScreen
import hu.bme.aut.android.todo.feature.todo_create.CreateTodoScreen
import hu.bme.aut.android.todo.feature.todo_list.TodosScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Todos.route
    ) {
        composable(Screen.Todos.route) {
            TodosScreen(
                onListItemClick = {
                    navController.navigate(Screen.CheckTodo.passId(it))
                },
                onFabClick = {
                    navController.navigate(Screen.CreateTodo.route)
                }
            )
        }
        composable(Screen.CreateTodo.route) {
            CreateTodoScreen(onNavigateBack = {
                navController.popBackStack(
                    route = Screen.Todos.route,
                    inclusive = true
                )
                navController.navigate(Screen.Todos.route)
            })
        }
        composable(
            route = Screen.CheckTodo.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            CheckTodoScreen(
                onNavigateBack = {
                    navController.popBackStack(
                        route = Screen.Todos.route,
                        inclusive = true
                    )
                    navController.navigate(Screen.Todos.route)
                }
            )
        }
    }
}