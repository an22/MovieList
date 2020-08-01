package com.an2.core.extensions

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}