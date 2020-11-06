package com.skillbox.files

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_shared_prefs.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedPreferencesFragment: Fragment(R.layout.fragment_shared_prefs) {

    private val sharedPrefs by lazy {
        requireContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val changeListener = SharedPreferences.OnSharedPreferenceChangeListener {
                sharedPreferences, key ->
            updateLoadedText()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            sharedPrefs.registerOnSharedPreferenceChangeListener(changeListener)
        }

        saveButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val textToSave = inputEditText.text.toString()
                sharedPrefs.edit()
                    .putString(KEY, textToSave)
                    .commit()
            }
        }

        loadButton.setOnClickListener { updateLoadedText() }
    }

    private fun updateLoadedText() {
        lifecycleScope.launch(Dispatchers.IO) {
            loadedText.text = sharedPrefs.getString(KEY, null)
        }
    }

    companion object {
        private const val SHARED_PREFS_NAME = "skillbox_shared_prefs"
        private const val KEY = "saved_text"
    }

}