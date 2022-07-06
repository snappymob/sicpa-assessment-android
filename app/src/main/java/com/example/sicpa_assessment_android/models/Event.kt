package com.example.sicpa_assessment_android.models

sealed class Event<out R> {
    object StartInitialLoad : Event<Nothing>()
    object RetryInitialLoad : Event<Nothing>()
    object ManualLoad : Event<Nothing>()
    object ForceReload : Event<Nothing>()
    object LoadNextPage : Event<Nothing>()
    class UpdateLoadedData<out T>(val data: T) : Event<T>()
    class LoadSuccess<out T>(val data: T) : Event<T>()
    class LoadFailure(val error: Throwable?) : Event<Nothing>()
}
