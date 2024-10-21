package com.weather.feature.forecast

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil3.load

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String) {
    view.load(imageUrl)
}