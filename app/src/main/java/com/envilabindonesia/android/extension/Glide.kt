package com.envilabindonesia.android.extension

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * Created by galihadityo on 2019-03-24.
 */

fun requestOptionCircle() = RequestOptions.circleCropTransform()

fun requestOptionRounded() = RequestOptions().apply { transform(CenterCrop(), RoundedCorners(10)) }

fun ImageView.load(uri: String) {
    Glide.with(context)
        .load(uri)
        .centerCrop()
        .placeholder(this.drawable)
        .into(this)
}

fun ImageView.loadCircle(bitmap: Bitmap) {
    Glide.with(context)
        .load(bitmap)
        .apply(requestOptionCircle())
        .placeholder(this.drawable)
        .into(this)
}

fun ImageView.loadCircle(uri: Uri) {
    Glide.with(context)
        .load(uri)
        .apply(requestOptionCircle())
        .placeholder(this.drawable)
        .into(this)
}

fun ImageView.loadCircle(uri: String) {
    Glide.with(context)
        .load(uri)
        .apply(requestOptionCircle())
        .placeholder(this.drawable)
        .into(this)
}

fun ImageView.loadCircle(@DrawableRes res: Int) {
    Glide.with(context)
        .load(res)
        .apply(requestOptionCircle())
        .placeholder(this.drawable)
        .into(this)
}

fun ImageView.loadRounded(bitmap: Bitmap) {
    Glide.with(context)
        .load(bitmap)
        .apply(requestOptionRounded())
        .placeholder(this.drawable)
        .into(this)
}

fun ImageView.loadRounded(uri: Uri) {
    Glide.with(context)
        .load(uri)
        .apply(requestOptionRounded())
        .placeholder(this.drawable)
        .into(this)
}

fun ImageView.loadRounded(uri: String) {
    Glide.with(context)
        .load(uri)
        .apply(requestOptionRounded())
        .placeholder(this.drawable)
        .into(this)
}

fun ImageView.loadRounded(@DrawableRes res: Int) {
    Glide.with(context)
        .load(res)
        .apply(requestOptionRounded())
        .placeholder(this.drawable)
        .into(this)
}