package com.an2.core.common.base.presentation.bindingadapter

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.an2.core.extensions.setOnSingleClickListener
import com.bumptech.glide.Glide


@BindingAdapter("linkClick")
fun View.moveToLinkOnClick(link: String?) {
    link ?: return
    this.setOnSingleClickListener {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        it.context.startActivity(browserIntent)
    }
}

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(link: String) {
    Glide.with(this)
        .load(link)
        .into(this)
}

@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else View.GONE
}

