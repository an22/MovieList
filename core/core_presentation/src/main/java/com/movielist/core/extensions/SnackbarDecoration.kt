package com.movielist.core.extensions

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.movielist.core.R

fun Snackbar.decorate(@ColorRes backgroundId: Int, @ColorRes textColorId: Int): Snackbar {
    val layout = view as Snackbar.SnackbarLayout

    val textView = with(layout) {
        setBackgroundColor(ContextCompat.getColor(context, backgroundId))
        findViewById<TextView>(R.id.snackbar_text)
    }

    with(textView) {
        setTextColor(ContextCompat.getColor(context, textColorId))
        maxLines = Int.MAX_VALUE
        ellipsize = null
    }

    with(layout.findViewById(R.id.snackbar_action) as TextView) {
        setTextColor(ContextCompat.getColor(context, textColorId))
    }

    return this
}
