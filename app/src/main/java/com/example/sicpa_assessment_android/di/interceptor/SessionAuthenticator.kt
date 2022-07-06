package com.example.sicpa_assessment_android.di.interceptor

import com.example.sicpa_assessment_android.models.event.SessionInvalidEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.greenrobot.eventbus.EventBus

class SessionAuthenticator(private val firebaseAuth: Any) : Authenticator {

    // TODO: Change these according to what backend used later.
    override fun authenticate(route: Route?, response: Response): Request? {
        if (!response.isSuccessful) {
//            val user = firebaseAuth.currentUser.guard {
//                EventBus.getDefault().post(SessionInvalidEvent())
//                return null
//            }
            when (response.code) {
                401 -> {
                    return runBlocking(Dispatchers.IO) {
                        try {
//                            val token = user.getIdToken(true).await().token
                            val originalRequest = response.request

                            originalRequest.newBuilder()
                                .removeHeader("Authorization")
//                                .addHeader("Authorization", "Bearer $token")
                                .build()
                        } catch (exception: Exception) {
                            EventBus.getDefault().post(SessionInvalidEvent())
                            null
                        }
                    }
                }
            }
        }
        return null
    }
}
