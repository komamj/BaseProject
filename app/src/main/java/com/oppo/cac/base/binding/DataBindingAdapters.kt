package com.oppo.cac.base.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.oppo.cac.base.BuildConfig
import com.oppo.cac.base.R


@Suppress("NAME_SHADOWING")
@BindingAdapter("image_url")
fun bindImageViewUrl(imageView: ImageView, thumbnailUrl: String?) {
    val thumbnailUrl = "${BuildConfig.THUMBNAIL_URL}$thumbnailUrl"
    Glide.with(imageView).load(thumbnailUrl).placeholder(R.color.colorPrimary).into(imageView)
}