package com.an2.core.common.base.presentation.bindingadapter

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.an2.core.extensions.setOnSingleClickListener
import com.bumptech.glide.Glide


@BindingAdapter("linkClick")
fun moveToLinkOnClick(view: View, link: String?) {
    link ?: return
    view.setOnSingleClickListener {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        it.context.startActivity(browserIntent)
    }
}

@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, link: String) {
    Glide.with(imageView)
        .load(link)
        .into(imageView)
}

@BindingAdapter("visibleOrGone")
fun visibleOrGone(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) {
        View.VISIBLE
    } else View.GONE
}

