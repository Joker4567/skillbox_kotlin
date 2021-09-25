package com.prognozrnm.utils.platform

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.prognozrnm.utils.R
import com.prognozrnm.utils.common.FullScreenDialog
import com.prognozrnm.utils.common.FullScreenDialog.Companion.DIALOG_FULL_SCREEN_TAG
import com.prognozrnm.utils.ext.*
import kotlinx.android.synthetic.main.layout_progress_bar.*
import kotlinx.android.synthetic.main.layout_toolbar_search_view.*

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    open val supportFragmentManager
        get() = requireActivity().supportFragmentManager

    open val screenViewModel: BaseViewModel? = null

    open val statusBarColor = R.color.colorTransparent
    open val statusBarLightMode = false

    open val setToolbar = false
    open val setDisplayHomeAsUpEnabled = true
    open val toolbarTitle: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStatusBar(statusBarColor, statusBarLightMode)

        screenViewModel?.let { vm ->
            observeEvent(vm.mainState, ::handleState)
        }
        if (setToolbar) {
            setupToolbar()
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

    protected fun setupStatusBar(statusBarColor: Int, statusBarLightMode: Boolean) {
        activity?.setStatusBarColor(requireActivity().getCompatColor(statusBarColor))
        activity?.setStatusBarLightMode(statusBarLightMode)
    }

    private fun setupToolbar() {
        toolbar?.let { (activity as AppCompatActivity).setSupportActionBar(it) }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(
            setDisplayHomeAsUpEnabled
        )
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = toolbarTitle
    }

    fun showSoftKeyboard(field: View?) {
        field?.let {
            val imm =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideSoftKeyboard() {
        view?.let {
            val imm =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun dialogNotAlreadyShown(tag: String) =
        supportFragmentManager.findFragmentByTag(tag) == null

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
}