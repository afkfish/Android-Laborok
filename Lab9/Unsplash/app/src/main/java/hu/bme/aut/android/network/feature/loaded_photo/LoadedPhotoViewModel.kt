package hu.bme.aut.android.network.feature.loaded_photo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.ExperimentalPagingApi
import hu.bme.aut.android.network.UnsplashApplication
import hu.bme.aut.android.network.data.repository.UnsplashPhotoDataSource
import hu.bme.aut.android.network.ui.model.UnsplashPhotoUiModel
import hu.bme.aut.android.network.ui.model.asLoadedPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@ExperimentalPagingApi
class LoadedPhotoViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: UnsplashPhotoDataSource
) : ViewModel() {

    private val photoId: String = checkNotNull(savedStateHandle["photoId"])


    private val _state = MutableStateFlow(LoadPhotoUiState())
    val state = _state.asStateFlow()

    init {
        loadPhoto(photoId)
    }

    private fun loadPhoto(
        photoId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = false)  }
            try {
                if (repository.exists(photoId)) {
                    _state.update { it.copy(
                        isLoading = false,
                        photo = repository.getPhotoByIdFromDatabase(photoId).map { photo -> photo.asLoadedPhoto() }
                    ) }
                } else {
                    _state.update { it.copy(
                            isLoading = false,
                            photo = repository.getPhotoByIdFromApi(photoId).map { photo -> photo.asLoadedPhoto() }
                    ) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(
                    isError = true,
                    throwable = e
                ) }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                LoadedPhotoViewModel(
                    savedStateHandle = savedStateHandle,
                    repository = UnsplashApplication.photoDataSource
                )
            }
        }
    }

}


data class LoadPhotoUiState(
    val isLoading: Boolean = false,
    val photo: Flow<UnsplashPhotoUiModel>? = null,
    val isError: Boolean = false,
    val throwable: Throwable? = null
)