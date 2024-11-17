package hu.bme.aut.android.network.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.network.R


enum class SearchState {
    IDLE, SEARCHING;
}

@ExperimentalMaterial3Api
@Composable
fun SearchTopAppBar(
    modifier: Modifier = Modifier,
    isScrollInProgress: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
) {

    var searchState by remember { mutableStateOf(SearchState.IDLE) }

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        elevation = if (isScrollInProgress) {
            0.dp
        } else 3.dp
    ) {
        AnimatedVisibility(
            visible = searchState == SearchState.IDLE,
            enter = slideInHorizontally {
                with(density) { screenWidth.roundToPx() }
            },
            exit = slideOutHorizontally {
                with(density) { screenWidth.roundToPx() }
            }
        ) {
            Box(
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = { searchState = SearchState.SEARCHING }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)

                }
            }
        }
        AnimatedVisibility(
            visible = searchState == SearchState.SEARCHING,
            enter = slideInHorizontally {
                with(density) { screenWidth.roundToPx() }
            },
            exit = slideOutHorizontally {
                with(density) { screenWidth.roundToPx() }
            }
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            searchState = SearchState.IDLE
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                },
                textStyle = MaterialTheme.typography.titleMedium,
                placeholder = {
                    Text(text = stringResource(id = R.string.search_bar_placeholder_text))
                },
                label = {
                    Text(text = stringResource(id = R.string.search_bar_label_text))
                },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun SearchTopAppBar_Preview() {
    MaterialTheme {
        SearchTopAppBar(
            isScrollInProgress = false,
            value = "",
            onValueChange = {

            }
        )
    }
}