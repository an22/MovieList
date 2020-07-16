package com.an2.core.common.base.presentation.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class AutoClearedValue<T : Any>(private val initializer:() -> T? = {null}) : ReadWriteProperty<Fragment, T>, LifecycleObserver {
    private var _value: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if(_value == null) {
            _value = initializer()
        }
        return _value ?: throw IllegalStateException("Trying to call an auto-cleared value outside of the view lifecycle.")
    }


    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        thisRef.viewLifecycleOwner.lifecycle.removeObserver(this)
        _value = value
        thisRef.viewLifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        _value = null
    }
}