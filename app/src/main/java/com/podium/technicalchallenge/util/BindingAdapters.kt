package com.podium.technicalchallenge.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.databinding.BindingAdapter
import com.amulyakhare.textdrawable.TextDrawable
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.podium.technicalchallenge.GetMovieQuery
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.ViewMoviePosterBinding
import com.podium.technicalchallenge.ui.details.DetailsFragment
import com.podium.technicalchallenge.ui.details.DetailsFragment.Companion.ARG_MOVIE_ID
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

    @JvmStatic
    private fun createPosterDialog(v: View, path: String) {
        val binding = ViewMoviePosterBinding.inflate(v.context.layoutInflater)

        val dialog = MaterialAlertDialogBuilder(v.context, R.style.MaterialAlertDialog_App)
            .setView(binding.root)
            .show()
        dialog.window?.also { win ->

            win.attributes?.also { lp ->
                lp.dimAmount = 0.8f
                win.attributes = lp

            }
            win.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.posterPath = path
        binding.movieImage.setOnClickListener {
            dialog.dismiss()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @BindingAdapter(value = ["click_listeners", "movie_id"], requireAll = false)
    @JvmStatic
    fun setMovieTaps(view: View, posterPath: String?, movieId: Int?) {
        posterPath?.let { path ->
            val gestureDetector =
                GestureDetector(view.context, object : GestureDetector.SimpleOnGestureListener() {
                    override fun onDoubleTap(e: MotionEvent?): Boolean {
                        createPosterDialog(view, path)
                        return super.onDoubleTap(e)
                    }

                    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                        movieId?.let {
                            val modalBottomSheet = DetailsFragment()
                            if (view.context is AppCompatActivity) {
                                modalBottomSheet.arguments = Bundle().apply {
                                    putInt(ARG_MOVIE_ID, movieId)
                                }
                                modalBottomSheet.show(
                                    (view.context as AppCompatActivity).supportFragmentManager,
                                    DetailsFragment.TAG
                                )
                            }
                        }
                        return super.onSingleTapConfirmed(e)
                    }
                })
            view.setOnTouchListener { v, event ->
                gestureDetector.onTouchEvent(event)
                v.onTouchEvent(event)
            }
        }
    }

    @BindingAdapter(value = ["from_html"])
    @JvmStatic
    fun setTextFromHtml(textView: TextView, string: String?) {
        if (string == null) {
            textView.text = null
        } else {
            textView.text = Html.fromHtml(string, FROM_HTML_MODE_LEGACY)
        }
    }

    //String.format(`Budget: $%.01f million`, viewModel.budget / 1000000.0f)
    @BindingAdapter(value = ["budget"])
    @JvmStatic
    fun setCast(textView: TextView, budget: Float?) {
        if (budget == null || budget.toInt() == 0) {
            textView.text = null
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
            textView.text =
                if (budget > 1000000) {
                    String.format("Budget: $%.01f million", budget / 1000000.0f)
                } else if (budget > 1000) {
                    String.format("Budget: $%.01f thousand", (budget / 1000.0f).toInt())
                } else {
                    String.format("Budget: $%d", budget.toInt())
                }
        }

    }

    @BindingAdapter(value = ["genres"])
    @JvmStatic
    fun setGenres(textView: TextView, genres: List<String>?) {
        if (genres == null || genres.isEmpty()) {
            textView.text = null
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
            textView.text = genres.joinToString(separator = ", ")
        }
    }

    @BindingAdapter(value = ["cast"])
    @JvmStatic
    fun setCast(textView: TextView, cast: List<GetMovieQuery.Cast>?) {
        if (cast == null) {
            textView.text = null
        } else {
            val joinToString = cast
                .sortedBy { it.order }
                .joinToString(
                    prefix = "<i>Cast</i><br>",
                    separator = "\n",
                    transform = { "${it.name} as <i>${it.character}</i><br>" }
                )
            textView.text = Html.fromHtml(joinToString, FROM_HTML_MODE_LEGACY)
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