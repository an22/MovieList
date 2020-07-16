package com.an2.core.extensions

import android.content.Context
import android.graphics.Point
import android.view.WindowManager


fun Context.getScreenWidthAndHeight(): Pair<Int, Int> {
    with(getSystemService(Context.WINDOW_SERVICE) as WindowManager) {
        val size = Point()
        defaultDisplay.getSize(size)
        return Pair(size.x, size.y)
    }
}