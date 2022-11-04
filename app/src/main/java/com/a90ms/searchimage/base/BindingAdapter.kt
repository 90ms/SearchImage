package com.a90ms.searchimage.base

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.a90ms.common.ext.isValidContext
import com.bumptech.glide.Glide

@BindingAdapter("bindImage")
fun ImageView.bindImage(
    url: String?
) {
    if (context.isValidContext()) {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}

@BindingAdapter("bindVisible")
fun View.bindVisible(show: Boolean?) {
    isVisible = show ?: false
}
