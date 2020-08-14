package com.movielist.core.common.base.presentation.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import java.util.*

class EventsQueue : MutableLiveData<Queue<Event>>() {

    @MainThread
    fun add(event: Event) {
        val queue = (value ?: LinkedList()).apply {
            add(event)
        }
        value = queue
    }

}