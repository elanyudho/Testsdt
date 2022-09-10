package com.elanyudho.testsdt.utils.extension

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.elanyudho.testsdt.R


fun ImageView.glide(view: View, image: Any) {
    try {
        Glide.with(view)
            .load(image)
            .error(R.color.teal_700)
            .into(this)
    } catch (ignored: Throwable) {
    }
}

fun ImageView.glide(context: Context, image: Any) {
    try {
        Glide.with(context)
            .load(image)
            .error(R.color.teal_700)
            .into(this)
    } catch (ignored: Throwable) {
    }
}

fun enable(view: View) {
    view.apply {
        isFocusable = true
        isClickable = true
        isEnabled = true
        invalidate()
    }
}

fun disable(view: View) {
    view.apply {
        isFocusable = false
        isClickable = false
        isEnabled = false
        invalidate()
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}