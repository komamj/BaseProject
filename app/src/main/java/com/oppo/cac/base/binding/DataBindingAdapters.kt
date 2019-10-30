package com.oppo.cac.base.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.oppo.cac.base.R

// TODO
private const val THUMBNAIL_URL = "http://139.198.4.126:8080/image/"

@Suppress("NAME_SHADOWING")
@BindingAdapter("image_url")
fun bindImageViewUrl(imageView: ImageView, thumbnailUrl: String?) {
    val thumbnailUrl = "$THUMBNAIL_URL$thumbnailUrl"
    Glide.with(imageView).load(thumbnailUrl).placeholder(R.color.colorPrimary).into(imageView)
}