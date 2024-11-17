package hu.bme.aut.android.network.feature.photos_feed.screensbysize

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import hu.bme.aut.android.network.ui.common.PhotoDetails
import hu.bme.aut.android.network.ui.common.PhotoItem
import hu.bme.aut.android.network.ui.common.SearchTopAppBar
import hu.bme.aut.android.network.ui.model.UnsplashPhotoUiModel
import hu.bme.aut.android.network.util.WindowSize

// JOYAXJ
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun PhotosFeedScreen_Expanded(
    onPhotoItemClick: (String) -> Unit,
    photos: LazyPagingItems<UnsplashPhotoUiModel>,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    loadedPhoto: UnsplashPhotoUiModel? = null,
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val state = rememberLazyGridState()

    Row(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    SearchTopAppBar(
                        isScrollInProgress = state.isScrollInProgress,
                        value = value,
                        onValueChange = onValueChange
                    )
                }
            ) {
                LazyVerticalGrid(
                    state = state,
                    columns = GridCells.Adaptive(240.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .weight(1f)
                ) {
                    items(photos.itemCount) { index ->
                        photos[index]?.let { model ->
                            PhotoItem(
                                photo = model,
                                onClick = onPhotoItemClick
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = loadedPhoto != null && screenWidth >= 1000.dp,
                enter = slideInHorizontally {
                    with(density) { 40.dp.roundToPx() }
                } + expandHorizontally(
                    expandFrom = Alignment.End
                ) + fadeIn(
                    initialAlpha = 0.3f
                ),
                exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .weight(1f)
            ) {
                PhotoDetails(
                    loadedPhoto = loadedPhoto!!,
                    windowSize = WindowSize.Expanded
                )
            }
        }
    }
}