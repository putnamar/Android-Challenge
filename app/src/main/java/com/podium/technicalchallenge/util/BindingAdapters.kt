package com.podium.technicalchallenge.util

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.amulyakhare.textdrawable.TextDrawable
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.ViewMoviePosterBinding
import com.squareup.picasso.Picasso

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

    @BindingAdapter(value = ["double_click"])
    @JvmStatic
    fun setDoubleTap(view: View, posterPath: String?) {
        posterPath?.also { path ->
            val gestureDetector =
                GestureDetector(null, object : GestureDetector.SimpleOnGestureListener() {
                    override fun onDoubleTap(e: MotionEvent?): Boolean {
                        val binding = ViewMoviePosterBinding.inflate(view.context.layoutInflater)

                        val dialog = MaterialAlertDialogBuilder(view.context, R.style.MaterialAlertDialog_App)
                            .setView(binding.root)
                            .show()
                        dialog.window?.also { win ->
                            win.attributes?.also { lp ->
                                lp.dimAmount = 0.8f
                                win.attributes = lp

                            }
                            win.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                        }

                        binding.posterPath = path
                        binding.movieImage.setOnClickListener {
                            dialog.dismiss()
                        }
                        return super.onDoubleTap(e)
                    }
                });

            view.setOnTouchListener { v, event ->
                gestureDetector.onTouchEvent(event)
                v.onTouchEvent(event)
            }
            view.setOnClickListener { v ->
//                val binding = ViewMoviePosterBinding.inflate(view.context.layoutInflater)
//                binding.posterPath = path
//                val dialog = MaterialAlertDialogBuilder(view.context, R.style.MaterialAlertDialog_App)
//                    .setView(binding.root)
//                    .show()
//                dialog.window?.also { win ->
//                    win.attributes?.also { lp ->
//                        lp.dimAmount = 0.8f
//                        win.attributes = lp
//                    }
//                    win.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
//                }

            }
        }
    }

    @BindingAdapter(value = ["runtime"])
    @JvmStatic
    fun setRuntime(textView: TextView, runtime: Int?) {
        runtime?.let { time ->
            val minutes = time % 60
            val hours = time / 60
            textView.text = textView.resources.getString(R.string.runtime_text, hours, minutes)
        }
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