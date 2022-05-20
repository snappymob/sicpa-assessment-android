package com.rrg.sicpa_test.utils

import androidx.lifecycle.MutableLiveData
import com.rrg.sicpa_test.core.Result
import com.rrg.sicpa_test.core.State
import timber.log.Timber
import java.io.IOException

fun checkInternetPingGoogle(): Boolean {
    try {
        val a: Int = Runtime.getRuntime().exec("ping -c 1 google.com").waitFor()
        return a == 0x0
    } catch (e: IOException) {
        Timber.e("Google Ping -> $e")
    } catch (e: InterruptedException) {
        Timber.e("Google Ping -> $e")
    }
    return false
}

fun <T> MutableLiveData<State<T>>.processNetworkResult(result: Result<T>){
    when (result) {
        is Result.Success -> {
            this.value = State.Loaded(result.value)
        }
        is Result.Failure -> {
            this.value = State.LoadingFailed(result.error)
        }
    }
}