package com.skillbox.files

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment: Fragment(R.layout.fragment_menu) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        assetFiles.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToAssetFilesFragment())
        }

        internalStorage.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToInternalStorageFragment())
        }

        externalStorage.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToExternalStorageFragment())
        }
        sharedPreferences.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToSharedPreferencesFragment())
        }

        datastore.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToDatastoreFragment())
        }

        homeWork.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToHomeWorkFragment())
        }
    }
}