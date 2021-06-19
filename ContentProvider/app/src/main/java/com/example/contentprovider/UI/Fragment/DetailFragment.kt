package com.example.contentprovider.UI.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.contentprovider.Data.FullContact
import com.example.contentprovider.Data.MainViewModel
import com.example.contentprovider.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var viewModel: MainViewModel
    private val args: DetailFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = MainViewModel(requireContext())
        val contact = args.contact
        bindViewModel()
        viewModel.getContactFullInfo(contact)

        detail_FAB.setOnClickListener {
            viewModel.deleteContact()
        }
    }

    private fun bindViewModel() {
        viewModel.contactLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) findNavController().popBackStack()
            else showInfo(it.first() as FullContact)
        }
    }

    private fun showInfo(contact: FullContact) {
        detail_textViewName.text = contact.name
        contact.numbers.forEach {
            detail_textViewNumber.append(it + "\n")
        }
        contact.emails.forEach {
            detail_textViewEmail.append(it + "\n")
        }
        Glide.with(fragment)
            .load(contact.avatar)
            .transform(CircleCrop())
            .placeholder(R.drawable.ic_baseline_image)
            .error(R.drawable.ic_baseline_image_not_supported)
            .into(detail_avatar)
    }
}