package dev.pimentel.template.data.di

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
import dev.pimentel.template.data.R
import dev.pimentel.template.data.generator.IdGenerator
import dev.pimentel.template.data.generator.IdGeneratorImpl
import dev.pimentel.template.data.repository.ExampleRepositoryImpl
import dev.pimentel.template.data.sources.local.ExampleLocalDataSource
import dev.pimentel.template.domain.repository.ExampleRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
    fun provideRetrofit(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): Retrofit {
        val apiUrl = context.getString(R.string.api_url)

        val client = OkHttpClient.Builder()
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
    // endregion

    // region LOCAL
    @Provides
    @Singleton
    fun provideIdGenerator(): IdGenerator = IdGeneratorImpl()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideExampleLocalDataSource(): ExampleLocalDataSource = ExampleLocalDataSource()
    // endregion

    // region REPOSITORY
    @Provides
    @Singleton
    fun provideExampleRepository(exampleLocalDataSource: ExampleLocalDataSource): ExampleRepository =
        ExampleRepositoryImpl(exampleLocalDataSource = exampleLocalDataSource)
    // endregion
}
