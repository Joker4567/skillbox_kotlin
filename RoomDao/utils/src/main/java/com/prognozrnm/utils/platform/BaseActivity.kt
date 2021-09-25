package com.prognozrnm.utils.platform

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.prognozrnm.utils.R
import com.prognozrnm.utils.common.FullScreenDialog
import com.prognozrnm.utils.common.FullScreenDialog.Companion.DIALOG_FULL_SCREEN_TAG
import com.prognozrnm.utils.ext.getCompatColor
import com.prognozrnm.utils.ext.observeEvent
import com.prognozrnm.utils.ext.setStatusBarColor
import com.prognozrnm.utils.ext.setStatusBarLightMode
import kotlinx.android.synthetic.main.layout_progress_bar.*


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
        screenViewModel?.let { vm ->
            observeEvent(vm.mainState, ::handleState)
        }
    }

    open fun handleState(state: State) {
        when (state) {
            is State.Loading -> {
                progressBar?.isVisible = true
            }
            is State.Loaded -> {
                progressBar?.isVisible = false
            }
            is State.Error -> {
                progressBar?.isVisible = false
                handleFailure(state.throwable)
            }
        }
    }

    internal fun dialogNotAlreadyShown(tag: String) =
        supportFragmentManager.findFragmentByTag(tag) == null

    open fun loadData() {}

    open fun handleOnlyFailure(state: State?) {
        if (state is State.Error) {
            handleFailure(state.throwable)
        }
    }

    open fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.ServerError -> if (dialogNotAlreadyShown(DIALOG_FULL_SCREEN_TAG)) FullScreenDialog.newInstance(
                R.layout.dialog_server_error,
                R.string.label_server_error,
                R.string.label_server_error_description
            ).show(supportFragmentManager, DIALOG_FULL_SCREEN_TAG)
            is Failure.CommonError -> if (dialogNotAlreadyShown(DIALOG_FULL_SCREEN_TAG)) FullScreenDialog.newInstance(
                R.layout.dialog_server_error,
                R.string.label_common_error,
                R.string.label_common_error_description
            ).show(supportFragmentManager, DIALOG_FULL_SCREEN_TAG)
            is Failure.NetworkConnection -> if (dialogNotAlreadyShown(DIALOG_FULL_SCREEN_TAG)) FullScreenDialog.newInstance(
                R.layout.fragment_connection_error
            ).show(supportFragmentManager, DIALOG_FULL_SCREEN_TAG)
        }
    }

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
