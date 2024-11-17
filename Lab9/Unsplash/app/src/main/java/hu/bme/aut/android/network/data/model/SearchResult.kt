package hu.bme.aut.android.network.data.model

import com.squareup.moshi.Json

data class SearchResult(
    @Json(name = "results") val photos: List<UnsplashPhoto>
)