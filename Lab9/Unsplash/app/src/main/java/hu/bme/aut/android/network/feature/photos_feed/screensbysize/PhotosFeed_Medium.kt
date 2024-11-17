package hu.bme.aut.android.network.feature.photos_feed.screensbysize

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import hu.bme.aut.android.network.ui.common.PhotoItem
import hu.bme.aut.android.network.ui.common.SearchTopAppBar
import hu.bme.aut.android.network.ui.model.UnsplashPhotoUiModel


@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun PhotosFeedScreen_Medium(
    refreshState: PullRefreshState,
    refreshing: Boolean,
    onPhotoItemClick: (String) -> Unit,
    photos: LazyPagingItems<UnsplashPhotoUiModel>,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberLazyGridState()
    Scaffold(
        modifier = modifier,
        topBar = {
            SearchTopAppBar(
                isScrollInProgress = state.isScrollInProgress,
                value = value,
                onValueChange = onValueChange
            )
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .pullRefresh(refreshState)
                .padding(it)
        ) {
            if (!refreshing) {
                LazyVerticalGrid(
                    state = state,
                    columns = GridCells.Fixed(2)
                ) {
                    items(photos.itemCount) { i ->
                        photos[i]?.let { model ->
                            PhotoItem(
                                photo = model,
                                onClick = onPhotoItemClick
                            )
                        }
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = refreshing,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
