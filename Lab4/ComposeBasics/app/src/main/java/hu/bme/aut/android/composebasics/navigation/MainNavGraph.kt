package hu.bme.aut.android.composebasics.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import hu.bme.aut.android.composebasics.feature.home.HomeScreen

@ExperimentalMaterial3Api
fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Home.route,
        route = MAIN_GRAPH_ROUTE
    ) {
        composable(
            route = Screen.Home.route,
            arguments = listOf(
                navArgument(Screen.Home.Args.username) {
                    type = NavType.StringType
                }
            )
        ) {
            HomeScreen(
                argument = navController.currentBackStackEntry?.arguments
                    ?.getString(Screen.Home.Args.username) ?: "",
                onLogout = {
                    navController.popBackStack(route = Screen.Login.route, inclusive = false)
                },
                onMenuItemClick = { navController.navigate(it) }
            )
        }
        composable(route = Screen.Profile.route) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Profile")
            }
        }
        composable(route = Screen.Settings.route) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Settings")
            }
        }
    }
}
