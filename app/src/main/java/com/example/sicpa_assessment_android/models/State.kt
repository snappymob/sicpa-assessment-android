package com.example.sicpa_assessment_android.models

sealed class State<out R> {
    object Initial : State<Nothing>()
    object Loading : State<Nothing>()
    class LoadingNextPage<out T>(val data: T) : State<T>()
    class LoadingFailed(val error: Throwable?) : State<Nothing>()
    object RetryingLoad : State<Nothing>()
    class Loaded<out T>(val data: T) : State<T>()
    class ManualReloading<out T>(val data: T) : State<T>()
}