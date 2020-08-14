package com.movielist.core.extensions

import android.os.SystemClock
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar

private const val DEFAULT_CLICK_TIMEOUT = 400L

private class OnSingleClickListener(
    private val timeout: Long,
    private val onClickListener: View.OnClickListener
) : View.OnClickListener {

    private var lastTimeClicked = 0L

    override fun onClick(v: View) {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastTimeClicked < timeout) return

        lastTimeClicked = currentTime
        onClickListener.onClick(v)
    }
}

private class OnSingleMenuItemClickListener(
    private val timeout: Long,
    private val listener: MenuItem.OnMenuItemClickListener
) : Toolbar.OnMenuItemClickListener {

    private var lastTimeClicked = 0L

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastTimeClicked < timeout) return true

        lastTimeClicked = currentTime

        return listener.onMenuItemClick(item)
    }
}

fun View.setOnSingleClickListener(timeout: Long = DEFAULT_CLICK_TIMEOUT, listener: (View) -> Unit) {
    this.setOnClickListener(OnSingleClickListener(timeout, View.OnClickListener { listener(it) }))
}

fun View.setOnSingleClickListener(
    listener: View.OnClickListener,
    timeout: Long = DEFAULT_CLICK_TIMEOUT
) {
    this.setOnClickListener(OnSingleClickListener(timeout, listener))
}

fun View.setOnSingleClickListener(listener: () -> Unit, timeout: Long = DEFAULT_CLICK_TIMEOUT) {
    this.setOnClickListener(OnSingleClickListener(timeout, View.OnClickListener { listener() }))
}

fun Toolbar.setOnSingleMenuItemClickListener(
        timeout: Long = DEFAULT_CLICK_TIMEOUT,
        listener: (MenuItem) -> Boolean
) {
    this.setOnMenuItemClickListener(
        OnSingleMenuItemClickListener(timeout, MenuItem.OnMenuItemClickListener { listener(it) })
    )
}
