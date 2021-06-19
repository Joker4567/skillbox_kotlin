package com.example.contentprovider.UI.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.example.contentprovider.Data.LoadViewModel
import com.example.contentprovider.R
import kotlinx.android.synthetic.main.fragment_load.*

class LoadFragment : Fragment(R.layout.fragment_load) {

    private lateinit var viewModel: LoadViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        intiViews()
    }

    private fun intiViews() {
        viewModel = LoadViewModel(requireContext())

        sendButton.setOnClickListener {
            viewModel.shareFile()
        }
        downloadButton.setOnClickListener {
            if (linkEditText.text.toString()
                    .isNotBlank()
            ) viewModel.getFile(url = linkEditText.text.toString(), context = requireContext())
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { loading ->
            if (loading) showLoading()
            else removeLoading()
        }
        viewModel.downloadedLiveData.observe(viewLifecycleOwner) { loaded ->
            sendButton.isEnabled = loaded
        }
        viewModel.intentLiveData.observe(viewLifecycleOwner) { intent ->
            startActivity(intent)
        }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        downloadButton.isEnabled = false
        linkEditText.isEnabled = false
        sendButton.isEnabled = false
    }

    private fun removeLoading() {
        progressBar.visibility = View.GONE
        downloadButton.isEnabled = true
        linkEditText.isEnabled = true
    }

}