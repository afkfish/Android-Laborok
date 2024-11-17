package hu.bme.aut.android.network

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.paging.ExperimentalPagingApi
import hu.bme.aut.android.network.navigation.NavGraph
import hu.bme.aut.android.network.ui.theme.UnsplashTheme
import hu.bme.aut.android.network.util.WindowSize

// JOYAXJ
class MainActivity : ComponentActivity() {
    @OptIn(
        ExperimentalMaterial3WindowSizeClassApi::class,
        ExperimentalMaterial3Api::class,
        ExperimentalPagingApi::class,
        ExperimentalMaterialApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnsplashTheme {
                val windowSize = when (calculateWindowSizeClass(this).widthSizeClass) {
                    WindowWidthSizeClass.Compact -> WindowSize.Compact
                    WindowWidthSizeClass.Medium -> WindowSize.Medium
                    WindowWidthSizeClass.Expanded -> WindowSize.Expanded
                    else -> WindowSize.Compact
                }

                NavGraph(windowSize = windowSize)
            }
        }
    }
}