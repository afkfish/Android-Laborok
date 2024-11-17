package hu.bme.aut.android.network.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.android.network.data.local.dao.UnsplashPhotoDao
import hu.bme.aut.android.network.data.local.dao.UnsplashPhotoRemoteKeysDao
import hu.bme.aut.android.network.data.model.UnsplashPhoto
import hu.bme.aut.android.network.data.model.UnsplashPhotoRemoteKeys

// JOYAXJ
@Database(entities = [UnsplashPhoto::class, UnsplashPhotoRemoteKeys::class], version = 1)
abstract class UnsplashDatabase : RoomDatabase() {
    abstract val photosDao: UnsplashPhotoDao
    abstract val remoteKeysDao: UnsplashPhotoRemoteKeysDao
}