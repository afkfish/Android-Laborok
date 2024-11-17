package hu.bme.aut.android.network.feature.photos_feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.map
import hu.bme.aut.android.network.UnsplashApplication
import hu.bme.aut.android.network.data.repository.UnsplashPhotoDataSource
import hu.bme.aut.android.network.ui.model.UnsplashPhotoUiModel
import hu.bme.aut.android.network.ui.model.asGridItemUiModel
import hu.bme.aut.android.network.ui.model.asLoadedPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@ExperimentalPagingApi
class PhotosFeedViewModel(
    private val repository: UnsplashPhotoDataSource
): ViewModel() {

    private val _state = MutableStateFlow(PopularPhotosUiState())
    val state = _state.asStateFlow()

    fun onSearchTermsChange(value: String) {
        _state.update { it.copy(searchTerms = value) }
        searchPhotos(state.value.searchTerms)
    }

    init {
        refreshPhotos()
    }

    fun refreshPhotos() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loadedPhotos = repository.getAllPhotos().map { pagingData ->
                    pagingData.map { photo -> photo.asGridItemUiModel() }
                }

                _state.update { it.copy(
                    isLoading = false,
                    photos = loadedPhotos
                ) }
            } catch (e: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    isError = true,
                    throwable = e
                ) }
            }
        }
    }

    fun loadSelectedPhoto(
        photoId: String
    ) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (repository.exists(photoId)) {
                    val loadedPhoto = repository.getPhotoByIdFromDatabase(photoId).map { photo ->
                        photo.asLoadedPhoto()
                    }
                    _state.update { it.copy(
                        isLoading = false,
                        photo = loadedPhoto
                    ) }
                } else {
                    val loadedPhoto = repository.getPhotoByIdFromApi(photoId).map { photo ->
                        photo.asLoadedPhoto()
                    }
                    _state.update { it.copy(
                        isLoading = false,
                        photo = loadedPhoto
                    )}
                }
            } catch (e: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    isError = true,
                    throwable = e
                ) }
            }
        }
    }

    private fun searchPhotos(query: String) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val searchedPhotos = repository.getSearchResults(query).map { pagingData ->
                    pagingData.map { photo ->
                        photo.asLoadedPhoto()
                    }
                }
                _state.update { it.copy(
                    isLoading = false,
                    photos = searchedPhotos
                ) }
            } catch (e: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    isError = true,
                    throwable = e
                ) }
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PhotosFeedViewModel(
                    repository = UnsplashApplication.photoDataSource,
                )
            }
        }
    }
}

data class PopularPhotosUiState(
    val isLoading: Boolean = false,
    val photos: Flow<PagingData<UnsplashPhotoUiModel>>? = null,
    val searchTerms: String = "",
    val photo: Flow<UnsplashPhotoUiModel>? = null,
    val isError: Boolean = false,
    val throwable: Throwable? = null
)