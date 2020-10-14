package com.skillbox.a10_fragment_

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.skillbox.a10_fragment_.helper.ItemSelectListener
import com.skillbox.a10_fragment_.helper.withArguments

class ListFragment: Fragment(R.layout.fragment_list) {

    private val itemSelectListener: ItemSelectListener?
        get() = parentFragment?.let { it as? ItemSelectListener }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view.let { it as ViewGroup }
            .children
            .mapNotNull { it as? Button }
            .forEach { button ->
                button.setOnClickListener {
                    onButtonClick(button.text.toString())
                }
            }
    }

    private fun onButtonClick(buttonText: String) {
        itemSelectListener?.onItemSelected(buttonText)
    }
}