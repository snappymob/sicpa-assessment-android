package com.example.sicpa_assessment_android.di.interceptor

import android.content.Context
import com.example.sicpa_assessment_android.BuildConfig
import com.example.sicpa_assessment_android.BuildConfig.API_KEY
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class ApiKeyInterceptor(val context: Context): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api-key", API_KEY)
            .build()

        // Request customization: add request headers
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}