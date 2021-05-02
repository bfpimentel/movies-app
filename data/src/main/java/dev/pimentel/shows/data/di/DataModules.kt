package dev.pimentel.shows.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.pimentel.series.data.R
import dev.pimentel.shows.data.database.SeriesDatabase
import dev.pimentel.shows.data.repository.ShowsRepositoryImpl
import dev.pimentel.shows.data.sources.local.ShowsLocalDataSource
import dev.pimentel.shows.data.sources.remote.ShowsRemoteDataSource
import dev.pimentel.shows.domain.repository.ShowsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModules {

    private const val REQUEST_TIMEOUT = 60L

    // region REMOTE
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context, moshi: Moshi): Retrofit {
        val apiUrl = context.getString(R.string.api_url)

        val client = OkHttpClient.Builder()
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()

        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideShowsRemoteDataSource(retrofit: Retrofit): ShowsRemoteDataSource = retrofit.create()
    // endregion

    // region LOCAL
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            SeriesDatabase::class.java,
            SeriesDatabase::class.simpleName!!
        ).build()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideShowsLocalDataSource(seriesDatabase: SeriesDatabase): ShowsLocalDataSource =
        seriesDatabase.createShowsLocalDataSource()
    // endregion

    // region REPOSITORY
    @Provides
    @Singleton
    fun provideShowsRepository(
        showsRemoteDataSource: ShowsRemoteDataSource,
        showsLocalDataSource: ShowsLocalDataSource
    ): ShowsRepository =
        ShowsRepositoryImpl(
            showsRemoteDataSource = showsRemoteDataSource,
            showsLocalDataSource = showsLocalDataSource
        )
    // endregion
}
