package hu.bme.aut.android.network.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import hu.bme.aut.android.network.data.model.UnsplashPhoto
import hu.bme.aut.android.network.data.remote.api.UnsplashApi
import hu.bme.aut.android.network.util.PagingUtil.INITIAL_PAGE_SIZE

// JOYAXJ
class SearchPagingSource(
    private val api: UnsplashApi,
    private val searchTerms: String
): PagingSource<Int, UnsplashPhoto>() {

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val currentPage = params.key ?: 1
        return try {
            val response = api.getSearchResults(searchTerms = searchTerms, perPage = INITIAL_PAGE_SIZE, page = currentPage)
            if (response.isSuccessful) {
                val photos = response.body()?.photos ?: emptyList()
                val endOfPaginationReached = photos.isEmpty()
                LoadResult.Page(
                    data = photos,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else throw Exception(response.errorBody()?.string() ?: "Unsuccessful request.")
        } catch (e: Exception) {
            Log.e("error",e.stackTraceToString())
            LoadResult.Error(e)
        }
    }
}