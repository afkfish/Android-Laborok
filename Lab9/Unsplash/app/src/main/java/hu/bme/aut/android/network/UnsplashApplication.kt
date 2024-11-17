package hu.bme.aut.android.network

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hu.bme.aut.android.network.data.local.database.UnsplashDatabase
import hu.bme.aut.android.network.data.remote.api.UnsplashApi
import hu.bme.aut.android.network.data.repository.UnsplashPhotoDataSource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalPagingApi
class UnsplashApplication : Application() {
    companion object {
        lateinit var photoDataSource: UnsplashPhotoDataSource
    }

    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(
            this.baseContext,
            UnsplashDatabase::class.java,
            "unsplash_db"
        ).build()

        val client = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val api = retrofit.create(UnsplashApi::class.java)

        photoDataSource = UnsplashPhotoDataSource(api,db)

    }
}