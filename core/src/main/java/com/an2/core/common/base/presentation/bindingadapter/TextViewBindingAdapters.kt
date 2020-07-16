package com.an2.core.common.base.presentation.bindingadapter

import android.graphics.Paint
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

@BindingAdapter("html")
@Suppress("deprecation")
fun setHtmlText(textView: AppCompatTextView, html: String) {
    textView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
        // we are using this flag to give a consistent behaviour
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(html)
    }
}

@BindingAdapter("html")
@Suppress("deprecation")
fun setHtmlText(textView: TextView, html: String) {
    if (html.contains("href")) {
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
    textView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
        // we are using this flag to give a consistent behaviour
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(html)
    }
}

@BindingAdapter("striked")
fun setStriked(textView: TextView, striked: Boolean) {
    if(striked){
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}