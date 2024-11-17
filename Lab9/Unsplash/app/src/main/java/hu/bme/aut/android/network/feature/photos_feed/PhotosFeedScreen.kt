package hu.bme.aut.android.network.feature.photos_feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import hu.bme.aut.android.network.feature.photos_feed.screensbysize.PhotosFeedScreen_Compact
import hu.bme.aut.android.network.feature.photos_feed.screensbysize.PhotosFeedScreen_Expanded
import hu.bme.aut.android.network.feature.photos_feed.screensbysize.PhotosFeedScreen_Medium
import hu.bme.aut.android.network.util.WindowSize


@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun PhotosFeedScreen(
    modifier: Modifier = Modifier,
    windowSize: WindowSize = WindowSize.Compact,
    onPhotoItemClick: (String) -> Unit = {},
    viewModel: PhotosFeedViewModel = viewModel(factory = PhotosFeedViewModel.Factory)
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val photos = state.photos?.collectAsLazyPagingItems()
    val selectedPhoto = state.photo?.collectAsStateWithLifecycle(null)

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val refreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = viewModel::refreshPhotos
    )

    if (photos != null) {
        when(windowSize) {
            WindowSize.Compact -> {
                PhotosFeedScreen_Compact(
                    refreshState = refreshState,
                    refreshing = state.isLoading,
                    onPhotoItemClick = onPhotoItemClick,
                    photos = photos,
                    value = state.searchTerms,
                    onValueChange = viewModel::onSearchTermsChange
                )
            }
            WindowSize.Medium -> {
                PhotosFeedScreen_Medium(
                    refreshState = refreshState,
                    refreshing = state.isLoading,
                    onPhotoItemClick = onPhotoItemClick,
                    photos = photos,
                    value = state.searchTerms,
                    onValueChange = viewModel::onSearchTermsChange
                )
            }
            WindowSize.Expanded -> {
                PhotosFeedScreen_Expanded(
                    onPhotoItemClick = if (screenWidth >= 1000.dp) {
                        viewModel::loadSelectedPhoto
                    } else onPhotoItemClick,
                    photos = photos,
                    loadedPhoto = selectedPhoto?.value,
                    value = state.searchTerms,
                    onValueChange = viewModel::onSearchTermsChange
                )
            }
        }
    } else if (state.isError) {
        Box(modifier = modifier.fillMaxSize()) {
            Text(text = state.throwable?.message.toString())
        }
    }
}