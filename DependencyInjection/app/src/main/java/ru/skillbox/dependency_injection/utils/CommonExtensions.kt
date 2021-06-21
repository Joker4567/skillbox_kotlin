package ru.skillbox.dependency_injection.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> ViewGroup.inflate(
    inflateBinding: (
        inflater: LayoutInflater,
        root: ViewGroup?,
        attachToRoot: Boolean
    ) -> T, attachToRoot: Boolean = false
): T {
    val inflater = LayoutInflater.from(context)
    return inflateBinding(inflater, this, attachToRoot)
}

fun Activity.hideKeyboardAndClearFocus() {
    val view = currentFocus ?: View(this)
    hideKeyboardFrom(view)
    view.clearFocus()
}

fun Context.hideKeyboardFrom(view: View) {
    getSystemService(Activity.INPUT_METHOD_SERVICE)
        .let { it as InputMethodManager }
        .hideSoftInputFromWindow(view.windowToken, 0)
}

fun <T : Fragment> T.toast(@StringRes message: Int) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun <T : Fragment> T.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun haveQ(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}