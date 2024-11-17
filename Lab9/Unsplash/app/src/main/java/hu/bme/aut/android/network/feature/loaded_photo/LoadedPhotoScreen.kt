package hu.bme.aut.android.network.feature.loaded_photo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import hu.bme.aut.android.network.R
import hu.bme.aut.android.network.ui.common.PhotoDetails


@ExperimentalPagingApi
@ExperimentalMaterialApi
@Composable
fun LoadedPhotoScreen(
    viewModel: LoadedPhotoViewModel = viewModel(factory = LoadedPhotoViewModel.Factory)
) {
    val state by viewModel.state.collectAsState()

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else if (state.isError) {
        val message = state.throwable?.message ?: stringResource(id = R.string.no_access_to_unsplash)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                textAlign = TextAlign.Center
            )
        }
    } else {
        val loadedPhoto = state.photo?.collectAsStateWithLifecycle(null)?.value
        if (loadedPhoto != null) {
            PhotoDetails(loadedPhoto = loadedPhoto)
        }
    }

}