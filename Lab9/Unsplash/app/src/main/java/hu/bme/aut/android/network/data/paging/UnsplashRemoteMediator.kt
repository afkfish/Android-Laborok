package hu.bme.aut.android.network.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import hu.bme.aut.android.network.data.local.database.UnsplashDatabase
import hu.bme.aut.android.network.data.model.UnsplashPhoto
import hu.bme.aut.android.network.data.model.UnsplashPhotoRemoteKeys
import hu.bme.aut.android.network.data.remote.api.UnsplashApi
import hu.bme.aut.android.network.util.PagingUtil.INITIAL_PAGE
import hu.bme.aut.android.network.util.PagingUtil.INITIAL_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class UnsplashRemoteMediator(
    private val api: UnsplashApi,
    private val db: UnsplashDatabase
): RemoteMediator<Int, UnsplashPhoto>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashPhoto>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeysForClosestToPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeysForLastItem(state) ?: throw InvalidObjectException("Result is empty")
                    remoteKeys.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val response = api.getPhotosFromEditorialFeed(page = page, perPage = INITIAL_PAGE_SIZE)
            var endOfPaginationReached = false
            if (response.isSuccessful) {
                val photos = response.body() ?: emptyList()
                endOfPaginationReached = photos.isEmpty()
                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.photosDao.deleteAllPhotos()
                        db.remoteKeysDao.deleteAllKeys()
                    }
                    val prevKey = if (page == INITIAL_PAGE) null else page - 1
                    val nextKey = if (photos.isEmpty()) null else page + 1
                    val keys = photos.map { photo ->
                        UnsplashPhotoRemoteKeys(
                            id = photo.id,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                    db.remoteKeysDao.insertAllKeys(keys)
                    db.photosDao.insertPhotos(photos)
                }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: InvalidObjectException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeysForLastItem(
        state: PagingState<Int, UnsplashPhoto>
    ): UnsplashPhotoRemoteKeys? {
        return state.lastItemOrNull()?.let { photo ->
            db.withTransaction {
                db.remoteKeysDao.getKeysById(photo.id)
            }
        }
    }

    private suspend fun getRemoteKeysForClosestToPosition(
        state: PagingState<Int, UnsplashPhoto>
    ): UnsplashPhotoRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.withTransaction {
                    db.remoteKeysDao.getKeysById(id)
                }
            }
        }
    }
}