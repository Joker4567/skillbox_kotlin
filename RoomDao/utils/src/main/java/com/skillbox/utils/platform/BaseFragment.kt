package com.skillbox.utils.platform

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.skillbox.utils.R
import com.skillbox.utils.ext.getCompatColor
import com.skillbox.utils.ext.setStatusBarColor
import com.skillbox.utils.ext.setStatusBarLightMode
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

        if (setToolbar) {
            setupToolbar()
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
}