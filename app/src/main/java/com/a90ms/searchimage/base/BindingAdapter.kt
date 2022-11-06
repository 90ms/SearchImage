package com.a90ms.searchimage.base

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.a90ms.common.ext.isValidContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("bindImage","bindPlaceHolder")
fun ImageView.bindImage(
    url: String?,
    placeHolder: Drawable? = null
) {
    if (context.isValidContext()) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions().error(placeHolder).placeholder(placeHolder).centerCrop())
            .into(this)
    }
}

@BindingAdapter("bindVisible")
fun View.bindVisible(show: Boolean?) {
    isVisible = show ?: false
}
