package com.example.sicpa_assessment_android.di

import android.content.Context
import com.example.sicpa_assessment_android.BuildConfig
import com.example.sicpa_assessment_android.BuildConfig.DEBUG
import com.example.sicpa_assessment_android.di.interceptor.ApiKeyInterceptor
import com.example.sicpa_assessment_android.di.interceptor.NetworkInterceptor
import com.example.sicpa_assessment_android.di.interceptor.SessionAuthenticator
import com.example.sicpa_assessment_android.di.interceptor.SessionInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptorHttpClient(
        sessionAuthenticator: SessionAuthenticator,
        sessionInterceptor: SessionInterceptor,
        networkInterceptor: NetworkInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        val connectTimeout: Long = 60
        val readTimeout: Long = 60

        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .addInterceptor(networkInterceptor)
            .addInterceptor(sessionInterceptor)
            .addInterceptor(apiKeyInterceptor)

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
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptorRetrofit(
        client: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideSessionInterceptor(): SessionInterceptor {
        // TODO: Change these according to what backend used later.
        return SessionInterceptor(Any())
    }

    @Provides
    @Singleton
    fun provideNetworkInterceptor(@ApplicationContext context: Context): NetworkInterceptor {
        return NetworkInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(@ApplicationContext context: Context): ApiKeyInterceptor {
        return ApiKeyInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideSessionAuthenticator(): SessionAuthenticator {
        // TODO: Change these according to what backend used later.
        return SessionAuthenticator(Any())
    }
}