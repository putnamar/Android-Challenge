package com.podium.technicalchallenge.util

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager

val Context.layoutInflater get() = LayoutInflater.from(this)

fun Activity.showInputMethod(view: View)
    {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.showSoftInput(
            view,
            0
        )
    }
