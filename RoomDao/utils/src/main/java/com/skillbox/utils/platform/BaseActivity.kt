package com.skillbox.utils.platform

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.utils.R
import com.skillbox.utils.ext.getCompatColor
import com.skillbox.utils.ext.setStatusBarColor
import com.skillbox.utils.ext.setStatusBarLightMode


abstract class BaseActivity(@LayoutRes val layoutResId: Int) : AppCompatActivity() {

    open val screenViewModel: BaseViewModel?
        get() = null

    open val statusBarColor = R.color.colorTransparent
    open val statusBarLightMode = true

    private val rootView
        get() = (this.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

    abstract fun initInterface(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        this.setStatusBarColor(this.getCompatColor(statusBarColor))
        this.setStatusBarLightMode(statusBarLightMode)

        initInterface(savedInstanceState)
        observeBaseLiveData()
    }

    open fun observeBaseLiveData() {

    }

    internal fun dialogNotAlreadyShown(tag: String) =
        supportFragmentManager.findFragmentByTag(tag) == null

    open fun loadData() {}

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    companion object {
        const val DIALOG_ERROR_TAG = "DialogError"
    }
}
