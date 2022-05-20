package com.rrg.sicpa_test.di

import com.rrg.sicpa_test.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class ApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val url = originalRequest.url.newBuilder()
            .addQueryParameter("api-key", BuildConfig.API_KEY)
            .build()


        val authenticatedRequest = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(authenticatedRequest)
    }
}