package com.example.sicpa_assessment_android.shared

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sicpa_assessment_android.models.Event
import com.example.sicpa_assessment_android.models.State
import com.example.sicpa_assessment_android.models.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class StatefulViewModel<T> : ViewModel() {

    private val _state = MutableStateFlow<State<T>>(State.Initial)
    val state = _state.asStateFlow()

    protected abstract suspend fun load(): Result<T>
    protected open suspend fun nextPage(): Result<T>? = null

    protected fun proceedToLoad() = viewModelScope.launch {
        transition(Event.StartInitialLoad)

        when (val result = load()) {
            is Result.Success -> {
                transition(Event.LoadSuccess(result.value))
            }
            is Result.Failure -> {
                transition(Event.LoadFailure(result.error))
            }
        }
    }

    //region Actions
    protected suspend fun retryInitialLoad() {
        transition(Event.RetryInitialLoad)
    }

    protected suspend fun reloadManually() {
        transition(Event.ManualLoad)
    }

    protected suspend fun forceReload() {
        transition(Event.ForceReload)
    }

    protected fun modifyLoadedData(data: T) = viewModelScope.launch {
        transition(Event.UpdateLoadedData(data))
    }

    protected fun loadNextPage() = viewModelScope.launch {
        if (state.value is State.LoadingNextPage) {
            // Abort loading next page if task is ongoing.
            return@launch
        }

        transition(Event.LoadNextPage)
        when (val result = nextPage()) {
            is Result.Failure -> transition(Event.LoadFailure(result.error))
            is Result.Success -> transition(Event.LoadSuccess(result.value))
            null -> transition(Event.LoadFailure(Exception("nextPage function returned null")))
        }
    }
    //endregion

    //region Transitions
    private suspend fun transition(event: Event<T>) {
        when {
            // Force state to loading and make api call again.
            event is Event.ForceReload -> {
                _state.emit(State.Loading)
                proceedToLoad()
            }
            /**
             * State.Initial
             */
            state.value is State.Initial && event is Event.StartInitialLoad -> {
                _state.emit(State.Loading)
            }
            /**
             * State.Loading
             */
            state.value is State.Loading && event is Event.LoadSuccess -> {
                _state.emit(State.Loaded(event.data))
            }
            state.value is State.Loading && event is Event.LoadFailure -> {
                _state.emit(State.LoadingFailed(event.error))
            }
            /**
             * State.Loaded
             */
            state.value is State.Loaded && event is Event.LoadNextPage -> {
                _state.emit(State.LoadingNextPage((state.value as State.Loaded<T>).data))
            }
            state.value is State.Loaded && event is Event.ManualLoad -> {
                _state.emit(State.ManualReloading((state.value as State.Loaded<T>).data))
                proceedToLoad()
            }
            state.value is State.Loaded && event is Event.LoadSuccess -> {
                _state.emit(State.Loaded(event.data))
            }
            state.value is State.Loaded && event is Event.UpdateLoadedData -> {
                _state.emit(State.Loaded(event.data))
            }
            /**
             * State.LoadingNextPage
             */
            state.value is State.LoadingNextPage && event is Event.LoadSuccess -> {
                _state.emit(State.Loaded(event.data))
            }
            state.value is State.LoadingNextPage && event is Event.LoadFailure -> {
                _state.emit(State.Loaded((state.value as State.LoadingNextPage<T>).data))
            }
            /**
             * State.LoadingFailed
             */
            state.value is State.LoadingFailed && event is Event.RetryInitialLoad -> {
                _state.emit(State.RetryingLoad)
                proceedToLoad()
            }
            state.value is State.LoadingFailed && event is Event.LoadFailure -> {
                _state.emit(State.LoadingFailed(event.error))
            }
            /**
             * State.RetryingLoad
             */
            state.value is State.RetryingLoad && event is Event.LoadSuccess -> {
                _state.emit(State.Loaded(event.data))
            }
            state.value is State.RetryingLoad && event is Event.LoadFailure -> {
                _state.emit(State.LoadingFailed(event.error))
            }
            /**
             * State.ManualReloading
             */
            state.value is State.ManualReloading && event is Event.LoadSuccess -> {
                _state.emit(State.Loaded(event.data))
            }
            state.value is State.ManualReloading && event is Event.LoadFailure -> {
                _state.emit(State.Loaded((state.value as State.ManualReloading<T>).data))
            }
            else -> {
                Log.e(this::class.java.name, "invalid event $event for state ${state.value} ")
            }
        }
    }
    //endregion
}
