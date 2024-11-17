package hu.bme.aut.android.network.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hu.bme.aut.android.network.data.model.UnsplashPhoto
import kotlinx.coroutines.flow.Flow

@Dao
interface UnsplashPhotoDao {
    @Insert
    suspend fun insertPhotos(photos: List<UnsplashPhoto>)

    @Query("SELECT EXISTS (SELECT * FROM photos WHERE id = :id)")
    fun exists(id: String): Flow<Boolean>

    @Query("DELETE FROM photos")
    suspend fun deleteAllPhotos()

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): PagingSource<Int, UnsplashPhoto>

    @Query("SELECT * FROM photos WHERE id = :id")
    fun getPhotoById(id: String): Flow<UnsplashPhoto>
}