package com.rrg.sicpa_test.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rrg.sicpa_test.BuildConfig
import com.rrg.sicpa_test.BuildConfig.DEBUG
import com.rrg.sicpa_test.api.Api
import com.rrg.sicpa_test.core.NetworkRequestManager
import com.rrg.sicpa_test.utils.DateTimeFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorRetrofit

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSessionInterceptor(): ApiKeyInterceptor{
        return  ApiKeyInterceptor()
    }

    @Provides
    @Singleton
    @AuthInterceptorOkHttpClient
    fun provideAuthInterceptorHttpClient(
        sessionInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        val connectTimeout: Long = 60
        val readTimeout: Long = 60

        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .addInterceptor(sessionInterceptor)

        if (DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    @AuthInterceptorRetrofit
    fun provideAuthInterceptorRetrofit(@AuthInterceptorOkHttpClient httpClient: OkHttpClient): Retrofit {
        val baseUrl = BuildConfig.BASE_URL
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(@AuthInterceptorRetrofit retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkRequestManager(): NetworkRequestManager {
        return NetworkRequestManager()
    }

    @Provides
    @Singleton
    fun provideDateTimeFormatter(): DateTimeFormatter{
        return DateTimeFormatter()
    }
}