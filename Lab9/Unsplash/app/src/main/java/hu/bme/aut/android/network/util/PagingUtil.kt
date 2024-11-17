package hu.bme.aut.android.network.util

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems

object PagingUtil {
    const val INITIAL_PAGE_SIZE = 10
    const val INITIAL_PAGE = 1

    fun <T: Any> LazyGridScope.items(
        items: LazyPagingItems<T>,
        key: ((item: T) -> Any)? = null,
        itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
    ) {
        items(
            count = items.itemCount,
            key = if (key == null) null else { index ->
                val item = items.peek(index)
                if (item == null) {
                    PagingPlaceholderKey(index)
                } else {
                    key(item)
                }
            }
        ) { index ->
            itemContent(items[index])
        }
    }

    data class PagingPlaceholderKey(private val index: Int) : Parcelable {
        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(index)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object {
            @Suppress("unused")
            @JvmField
            val CREATOR: Parcelable.Creator<PagingPlaceholderKey> =
                object : Parcelable.Creator<PagingPlaceholderKey> {
                    override fun createFromParcel(parcel: Parcel) =
                        PagingPlaceholderKey(parcel.readInt())

                    override fun newArray(size: Int) = arrayOfNulls<PagingPlaceholderKey?>(size)
                }
        }
    }
}