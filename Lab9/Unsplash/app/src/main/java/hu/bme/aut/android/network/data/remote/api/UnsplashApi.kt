package hu.bme.aut.android.network.data.remote.api

import hu.bme.aut.android.network.data.model.SearchResult
import hu.bme.aut.android.network.data.model.UnsplashPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    @GET("/photos")
    suspend fun getPhotosFromEditorialFeed(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("client_id") clientId: String = "n1r0WrIP6pzNiOjGVAilY5OO1TznpxerkeenrLcQs6w",
    ): Response<List<UnsplashPhoto>>

    @GET("/photos/{id}")
    suspend fun getPhotoById(
        @Path("id") id: String,
        @Query("client_id") clientId: String = "n1r0WrIP6pzNiOjGVAilY5OO1TznpxerkeenrLcQs6w",
    ): Response<UnsplashPhoto>

    @GET("/search/photos")
    suspend fun getSearchResults(
        @Query("client_id") clientId: String = "n1r0WrIP6pzNiOjGVAilY5OO1TznpxerkeenrLcQs6w",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("query") searchTerms: String,
    ): Response<SearchResult>

}