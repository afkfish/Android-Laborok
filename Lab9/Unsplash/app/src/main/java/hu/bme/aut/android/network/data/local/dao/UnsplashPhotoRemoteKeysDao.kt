package hu.bme.aut.android.network.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.network.data.model.UnsplashPhotoRemoteKeys

@Dao
interface UnsplashPhotoRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(keys: List<UnsplashPhotoRemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getKeysById(id: String): UnsplashPhotoRemoteKeys

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllKeys()
}