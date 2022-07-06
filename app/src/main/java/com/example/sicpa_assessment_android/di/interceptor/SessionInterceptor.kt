package com.example.sicpa_assessment_android.di.interceptor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class SessionInterceptor(private val firebaseAuth: Any) : Interceptor {

    // TODO: Change these according to what backend used later.
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithConfigurationInfo = chain.request().newBuilder().build()

//        val user = firebaseAuth.currentUser.guard {
//            return chain.proceed(requestWithConfigurationInfo)
//        }

        return runBlocking(Dispatchers.IO) {
            try {
//                val token = user.getIdToken(false).await().token
                val authenticatedRequest = requestWithConfigurationInfo.newBuilder()
//                    .addHeader("Authorization", "Bearer $token")
                    .build()

                chain.proceed(authenticatedRequest)
            } catch (exception: Exception) {
                chain.proceed(requestWithConfigurationInfo)
            }
        }
    }
}
