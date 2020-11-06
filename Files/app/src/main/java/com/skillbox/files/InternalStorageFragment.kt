package com.skillbox.files

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.regex.Pattern

class InternalStorageFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        internalStorageCacheExample()
        internalStorageFilesExample()
    }

    private fun internalStorageCacheExample() {
        lifecycleScope.launch(Dispatchers.IO) {
            val cacheDir = requireContext().cacheDir
            Log.d("InternalStorageFragment", "cacheDir path = ${cacheDir.absolutePath}")
            val cacheFile = File(cacheDir, "test_cache.txt")
            try {
                cacheFile.outputStream().buffered().use {
                    it.write("Content in cache file".toByteArray())
                }
            } catch (t: Throwable) {}
        }
    }

    private fun internalStorageFilesExample() {
        lifecycleScope.launch(Dispatchers.IO) {
            val filesDir = requireContext().filesDir
            Log.d("InternalStorageFragment", "filesDir path = ${filesDir.absolutePath}")
            val file = File(filesDir, "test_cache.txt")
            try {
                file.outputStream().buffered().use {
                    it.write("Content in file".toByteArray())
                }
            } catch (t: Throwable) {}
        }
    }
}