@file:Suppress("DEPRECATION")

package com.skillbox.utils.ext

import android.app.Activity
import android.view.View
import androidx.annotation.ColorInt

fun Activity.setStatusBarColor(@ColorInt color: Int) {
    window.statusBarColor = color
}

fun Activity.setStatusBarLightMode(
    isLightMode: Boolean
) {
    val decorView = this.window.decorView
    var vis = decorView.systemUiVisibility
    vis = if (isLightMode) {
        vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
    decorView.systemUiVisibility = vis
}