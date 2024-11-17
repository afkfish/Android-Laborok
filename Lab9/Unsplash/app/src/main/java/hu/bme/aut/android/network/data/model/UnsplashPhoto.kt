package hu.bme.aut.android.network.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "photos")
data class UnsplashPhoto(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val likes: Int,
    @Embedded
    val user: UserData,
    @Embedded
    val urls: Urls
)

data class UserData(
    val username: String,
    val name: String,
    @Json(name = "total_likes")
    val totalLikes: Int,
    @Json(name = "total_photos")
    val totalPhotos: Int,
    @Json(name = "profile_image") @Embedded
    val profileImage: UserProfileImage,
)

data class UserProfileImage(
    val small: String,
    val medium: String,
    val large: String
)

data class Urls(
    val regular: String,
    val full: String
)