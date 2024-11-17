package hu.bme.aut.android.network.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import hu.bme.aut.android.network.data.local.database.UnsplashDatabase
import hu.bme.aut.android.network.data.model.UnsplashPhoto
import hu.bme.aut.android.network.data.paging.SearchPagingSource
import hu.bme.aut.android.network.data.paging.UnsplashRemoteMediator
import hu.bme.aut.android.network.data.remote.api.UnsplashApi
import hu.bme.aut.android.network.util.PagingUtil.INITIAL_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

@ExperimentalPagingApi
class UnsplashPhotoDataSource(
    private val api: UnsplashApi,
    private val db: UnsplashDatabase
) {

    fun getAllPhotos(): Flow<PagingData<UnsplashPhoto>> {
        return Pager(
            config = PagingConfig(pageSize = INITIAL_PAGE_SIZE),
            remoteMediator = UnsplashRemoteMediator(api, db),
            pagingSourceFactory = {  db.photosDao.getAllPhotos() }
        ).flow
    }

    fun getPhotoByIdFromDatabase(id: String): Flow<UnsplashPhoto> = db.photosDao.getPhotoById(id)

    fun getSearchResults(searchTerms: String): Flow<PagingData<UnsplashPhoto>> {
        return Pager(
            config = PagingConfig(pageSize = INITIAL_PAGE_SIZE),
            pagingSourceFactory = {
                SearchPagingSource(
                    api = api,
                    searchTerms = searchTerms
                )
            }
        ).flow
    }

    suspend fun exists(id: String): Boolean = db.photosDao.exists(id).first()

    suspend fun getPhotoByIdFromApi(id: String): Flow<UnsplashPhoto> {
        val response = api.getPhotoById(id)
        return if (response.isSuccessful) {
            flow { emit(response.body() ?: throw NullPointerException()) }
        } else throw Exception("Unsuccessful request")
    }
}