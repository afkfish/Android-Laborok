package hu.bme.aut.android.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import hu.bme.aut.android.todo.ui.theme.TodoTheme
import hu.bme.aut.android.todo.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                NavGraph()
            }
        }
    }
}
