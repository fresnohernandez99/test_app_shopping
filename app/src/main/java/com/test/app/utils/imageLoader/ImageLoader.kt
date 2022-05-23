package com.test.app.utils.imageLoader

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.test.core.GlideApp

class ImageLoader(private val context: Context) {

    fun loadDrawable(imgName: String, imageView: ImageView, center: Boolean) {
        val img = ContextCompat.getDrawable(
            context,
            context.resources.getIdentifier(imgName, "drawable", context.packageName)
        )
        val charge =
            GlideApp.with(context)
                .load(img)
        if (center) {
            charge
                .centerCrop()
                .into(imageView)
        } else charge.into(imageView)
    }

    fun loadBitmap(imgName: Bitmap?, imageView: ImageView, center: Boolean) {
        val charge =
            GlideApp.with(context)
                .load(imgName)
        if (center) {
            charge
                .centerCrop()
                .into(imageView)
        } else charge.into(imageView)
    }

    fun loadUri(uri: Uri, imageView: ImageView, center: Boolean) {
        val charge =
            GlideApp.with(context)
                .load(uri)
        if (center) {
            charge
                .centerCrop()
                .into(imageView)
        } else charge.into(imageView)
    }

    fun loadUrl(url: String, imageView: ImageView, center: Boolean) {
        val charge =
            GlideApp.with(context)
                .load(url)
        if (center) {
            charge
                .centerCrop()
                .into(imageView)
        } else charge.into(imageView)
    }
}