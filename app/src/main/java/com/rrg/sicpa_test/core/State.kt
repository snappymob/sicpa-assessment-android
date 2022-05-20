package com.rrg.sicpa_test.core

sealed class State<out R> {
    object Loading : State<Nothing>()
    class LoadingFailed(val error: AppError?) : State<Nothing>()
    class Loaded<out T>(val data: T) : State<T>()
}