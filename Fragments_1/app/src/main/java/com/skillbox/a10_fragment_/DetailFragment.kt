package com.skillbox.a10_fragment_

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skillbox.a10_fragment_.helper.MainListener
import com.skillbox.a10_fragment_.helper.withArguments
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? MainListener)?.onClick("[DetailFragment] onStart")
        inputTextView.text = requireArguments().getString(KEY_TEXT)
    }

    override fun onPause() {
        super.onPause()
        (activity as? MainListener)?.onClick("[DetailFragment] onPause")
    }

    companion object {

        private const val KEY_TEXT = "key_text"

        fun newInstance(text: String): DetailFragment {
            return DetailFragment().withArguments {
                putString(KEY_TEXT, text)
            }
        }
    }
}