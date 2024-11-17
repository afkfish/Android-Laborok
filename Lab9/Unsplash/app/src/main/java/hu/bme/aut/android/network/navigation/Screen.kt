package hu.bme.aut.android.network.navigation

sealed class Screen(val route: String) {
    object PhotosFeed: Screen("photos_feed")
    object LoadedPhoto: Screen("loaded_photo/{photoId}") {
        fun passPhotoId(photoId: String) = "loaded_photo/$photoId"
    }
}