@file:Suppress("unused")

package com.movielist.core.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.movielist.core.common.base.presentation.viewmodel.Event
import com.movielist.core.common.base.presentation.viewmodel.EventsQueue
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Fragment.observe(eventsQueue: EventsQueue, eventHandler: (Event) -> Unit) {
    eventsQueue.observe(viewLifecycleOwner, Observer { queue ->
        while (queue != null && queue.isNotEmpty()) {
            eventHandler(queue.remove())
        }
    })
}

fun AppCompatActivity.observe(eventsQueue: EventsQueue, eventHandler: (Event) -> Unit) {
    eventsQueue.observe(this, Observer { queue ->
        while (queue != null && queue.isNotEmpty()) {
            eventHandler(queue.remove())
        }
    })
}

fun <T : Any> MutableLiveData<T>.delegate(): ReadWriteProperty<Any, T> {
    return object : ReadWriteProperty<Any, T> {
        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = onNext(value)
        override fun getValue(thisRef: Any, property: KProperty<*>): T = requireValue()
    }
}

fun <T> MutableLiveData<T>.onNext(next: T) {
    this.value = next
}

fun <T : Any> LiveData<T>.requireValue(): T = checkNotNull(value)


inline fun <reified T, LD : LiveData<T>> Fragment.observe(
    liveData: LD,
    crossinline block: (T) -> Unit
) {
    liveData.observe(viewLifecycleOwner, Observer { block(it) })
}

inline fun <reified T, LD : LiveData<T>> AppCompatActivity.observe(
    liveData: LD,
    crossinline block: (T) -> Unit
) {
    liveData.observe(this, Observer { block(it) })
}

inline fun <X, Y> MutableLiveData<X>.mutableMap(crossinline transform: (X) -> Y): MutableLiveData<Y> {
    val result: MediatorLiveData<Y> = MediatorLiveData<Y>()
    result.addSource(
        this
    ) { x -> result.setValue(transform(x)) }
    return result
}

inline fun <X, Y> LiveData<X>.mapDistinct(crossinline transform: (X) -> Y): LiveData<Y> {
    return map(transform).distinctUntilChanged()
}
