package hu.bme.aut.android.network.ui.model

import hu.bme.aut.android.network.data.model.UnsplashPhoto

data class UnsplashPhotoUiModel(
    val id: String,
    val photoUrl: String,
    val likes: Int,
    val username: String,
    val userProfileImageUrl: String
)

fun UnsplashPhoto.asGridItemUiModel() = UnsplashPhotoUiModel(
    id = id,
    photoUrl = urls.regular,
    likes = likes,
    username = user.username,
    userProfileImageUrl = user.profileImage.medium
)

fun UnsplashPhoto.asLoadedPhoto() = UnsplashPhotoUiModel(
    id = id,
    photoUrl = urls.full,
    likes = likes,
    username = user.username,
    userProfileImageUrl = user.profileImage.large
)