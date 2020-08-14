package com.movielist.core.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val eventsQueue = EventsQueue()

    protected open val viewModelScopeErrorHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.e(this::class.java.simpleName, throwable.message, throwable)
            //todo: handle throwable
        }


    protected fun viewModelScopeSafeLaunch(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(viewModelScopeErrorHandler) {
            block.invoke(this)
        }
    }
}