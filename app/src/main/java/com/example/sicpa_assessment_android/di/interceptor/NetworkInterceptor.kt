package com.example.sicpa_assessment_android.di.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

// This will only verify if the WiFi or Cellular Data is ON, but that doesn’t guarantee if there’s internet.
// TODO: Improve network status checking.
class NetworkInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (isConnectionAvailable()) {
            val originalRequest = chain.request()
            return chain.proceed(originalRequest)
        } else {
            throw IOException()
        }
    }

    private fun isConnectionAvailable(): Boolean {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        val network = connectivityManager.activeNetwork
        val connection = connectivityManager.getNetworkCapabilities(network)

        return connection != null && (
            connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            )
    }
}
