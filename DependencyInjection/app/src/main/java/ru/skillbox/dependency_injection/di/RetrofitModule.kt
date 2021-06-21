package ru.skillbox.dependency_injection.di

import android.app.Application
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.skillbox.dependency_injection.data.Api
import ru.skillbox.dependency_injection.data.AppVersionInterceptor
import ru.skillbox.dependency_injection.utils.allowReads
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val TIME_OUT_CONNECT = 10L
    private const val TIME_OUT_WRITE = 10L
    private const val TIME_OUT_READ = 10L

    @Singleton
    @Provides
    fun provideCache(application: Application): Cache {
        return allowReads {
            val cacheSize = 10 * 1024 * 1024
            Cache(application.cacheDir, cacheSize.toLong())
        }
    }

    @Provides
    fun buildOkHttp(cache: Cache, headerInterceptor: AppVersionInterceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        with(okHttpClientBuilder) {
            addInterceptor(headerInterceptor)
            cache(cache)
            connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT_WRITE, TimeUnit.SECONDS)
            readTimeout(TIME_OUT_READ, TimeUnit.SECONDS)
            followRedirects(false)
            retryOnConnectionFailure(true)
        }
        val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
                .client(client)
                .baseUrl("https://google.com")
    }

    @Singleton
    @Provides
    fun provideRetrofitServiceCatalog(retrofit: Retrofit.Builder): Api {
        return retrofit
                .build()
                .create(Api::class.java)
    }
}
