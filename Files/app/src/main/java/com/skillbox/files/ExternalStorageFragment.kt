package com.skillbox.files

import android.app.DownloadManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.skillbox.data.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ExternalStorageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        downloadFileExample()
    }

    private fun externalStorageExample() {
        lifecycleScope.launch(Dispatchers.IO) {
            if(Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@launch

            val testFolder = requireContext().getExternalFilesDir("testFolder")
            val testFile = File(testFolder, "external_test_file.txt")
            try {
                testFile.outputStream().buffered().use {
                    it.write("Content in external storage file".toByteArray())
                }
            } catch (t: Throwable) {}
        }
    }

    private fun downloadFileExample() {
        lifecycleScope.launch(Dispatchers.IO) {
            if(Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@launch

            val testFolder = requireContext().getExternalFilesDir("testFolder")
            val testFile = File(testFolder, "external_test_file.txt")

            try {
                testFile.outputStream().use { fileOutputStream ->
                    Networking.api
                        .getFile("https://github.com/Kotlin/kotlinx.coroutines/raw/master/README.md")
                        .byteStream()
                        .use { inputStream ->
                            inputStream.copyTo(fileOutputStream)
                        }
                }
            } catch (t: Throwable) {

            }
        }
    }
}