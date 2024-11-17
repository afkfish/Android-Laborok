package hu.bme.aut.android.network.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import hu.bme.aut.android.network.feature.loaded_photo.LoadedPhotoScreen
import hu.bme.aut.android.network.feature.photos_feed.PhotosFeedScreen
import hu.bme.aut.android.network.util.WindowSize

@ExperimentalMaterial3Api
@ExperimentalPagingApi
@ExperimentalMaterialApi
@Composable
fun NavGraph(
    windowSize: WindowSize = WindowSize.Compact,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.PhotosFeed.route
    ) {
        composable(route = Screen.PhotosFeed.route) {
            PhotosFeedScreen(
                windowSize = windowSize,
                onPhotoItemClick = { photoId ->
                    navController.navigate(Screen.LoadedPhoto.passPhotoId(photoId))
                }
            )
        }
        composable(
            route = Screen.LoadedPhoto.route,
            arguments = listOf(
                navArgument("photoId") {
                    type = NavType.StringType
                }
            )
        ) {
            LoadedPhotoScreen()
        }
    }
}