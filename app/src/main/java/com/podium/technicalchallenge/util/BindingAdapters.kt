package com.podium.technicalchallenge.util

import android.content.Context
import android.content.res.Resources
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.amulyakhare.textdrawable.TextDrawable
import com.squareup.picasso.Picasso
import android.content.res.TypedArray
import android.graphics.Color

object BindingAdapters {
    private fun getMatColor(typeColor: String, context: Context): Int {
        var returnColor: Int = Color.BLACK
        val arrayId: Int = context.resources.getIdentifier(
            "mdcolor_$typeColor",
            "array",
            context.packageName
        )
        if (arrayId != 0) {
            val colors: TypedArray = context.resources.obtainTypedArray(arrayId)
            val index = (Math.random() * colors.length()).toInt()
            returnColor = colors.getColor(index, Color.BLACK)
            colors.recycle()
        }
        return returnColor
    }
    @BindingAdapter(value = ["lazy_src", "title"])
    @JvmStatic
    fun setPosterPath(imageView: ImageView, posterPath: String?, title: String?) {
        posterPath?.let { path ->
            val placeholder = TextDrawable.builder()
                .beginConfig()
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRect(title?.substring(0, 1).orEmpty(), getMatColor("500", imageView.context))
            Picasso.get().load(path)
                .placeholder(placeholder)
                .into(imageView)
        }
    }
}