package com.example.contentprovider.UI.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.contentprovider.Data.Contact
import com.example.contentprovider.Data.FullContact
import com.example.contentprovider.Data.MainViewModel
import com.example.contentprovider.R
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment(R.layout.fragment_add) {

    private lateinit var viewModel: MainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init(){
        viewModel = MainViewModel(requireContext())

        buttonSave.setOnClickListener {
            saveContact()
        }

        viewModel.loadLiveData.observe(viewLifecycleOwner) {
            if (it) findNavController().popBackStack()
        }
    }

    private fun saveContact() {
        if (editEmail.text.isBlank().not()) {
            viewModel.addContact(FullContact(0L, editName.text.toString(), arrayListOf(editNumber.text.toString()), arrayListOf(editEmail.text.toString()), ""))
        } else {
            viewModel.addContact(Contact(0L, editName.text.toString(), arrayListOf(editNumber.text.toString()), ""))
        }
    }
}
