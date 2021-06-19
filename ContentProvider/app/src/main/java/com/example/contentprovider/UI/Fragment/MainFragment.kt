package com.example.contentprovider.UI.Fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contentprovider.Data.Contact
import com.example.contentprovider.Data.MainViewModel
import com.example.contentprovider.R
import com.example.contentprovider.UI.Adapter.ContactAdapter
import com.example.contentprovider.Utils.autoCleared
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var viewModel: MainViewModel

    private var contactAdapter: ContactAdapter by autoCleared()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = MainViewModel(requireContext())
        bindViewModel()
        initViews()
        viewModel.getContacts()
        checkPermissions()
    }

    private fun checkPermissions() {
        val readGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

        val writeGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

        if (readGranted.not() && writeGranted.not()) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS),
                PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            viewModel.getContacts()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS),
                PERMISSIONS_REQUEST_CODE
            )
        }
    }

    private fun initViews() {
        FABAdd.setOnClickListener {
            openAddContact()
        }

        FABLoad.setOnClickListener {
            openLoadFragment()
        }

        contactAdapter =
            ContactAdapter(onItemClick = { position ->
                openContactDetailInfo(position)
            })
        recyclerView.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun openLoadFragment() {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoadFragment())
    }

    private fun openContactDetailInfo(position: Int) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                viewModel.contactLiveData.value?.get(position)!! as Contact
            )
        )
    }

    private fun openAddContact() {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddFragment())
    }

    private fun bindViewModel() {
        viewModel.contactLiveData.observe(viewLifecycleOwner) {
            contactAdapter.items = it as List<Contact>
        }
        viewModel.loadLiveData.observe(viewLifecycleOwner) { loading ->
            if (loading) showLoading()
            else hideLoading()
        }
    }

    private fun showLoading() {
        recyclerView.isEnabled = false
        FABAdd.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        recyclerView.isEnabled = true
        FABAdd.isEnabled = true
        progressBar.visibility = View.GONE
    }

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 214
    }

}