package hu.bme.aut.android.composebasics.feature.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import hu.bme.aut.android.composebasics.R
import hu.bme.aut.android.composebasics.navigation.Screen

enum class MenuItemUiModel(
    val text: @Composable () -> Unit,
    val icon: @Composable () -> Unit,
    val screenRoute: String
) {
    PROFILE(
        text = { Text(text = stringResource(id = R.string.dropdown_menu_item_label_profile))},
        icon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = null)
        },
        screenRoute = Screen.Profile.route
    ),
    SETTINGS(
        text = { Text(text = stringResource(id = R.string.dropdown_menu_item_label_settings))},
        icon = {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
        },
        screenRoute = Screen.Settings.route
    )
}