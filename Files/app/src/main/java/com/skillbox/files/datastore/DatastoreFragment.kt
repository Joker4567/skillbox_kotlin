package com.skillbox.files.datastore

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.skillbox.files.R
import kotlinx.android.synthetic.main.fragment_shared_prefs.*

class DatastoreFragment: Fragment(R.layout.fragment_shared_prefs) {

    private val viewModel by viewModels<DatastoreViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        saveButton.setOnClickListener {
            viewModel.save(inputEditText.text.toString())
        }
        viewModel.textLiveData.observe(viewLifecycleOwner, Observer { loadedText.text = "key: $it" })
    }

}