package hu.bme.aut.android.composebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.composebasics.navigation.NavGraph
import hu.bme.aut.android.composebasics.ui.theme.ComposeBasicsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicsTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}