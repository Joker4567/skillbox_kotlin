package com.skillbox.files.homework

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.skillbox.files.R
import kotlinx.android.synthetic.main.fragment_home_work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.nio.file.Paths

class HomeworkFragment : Fragment(R.layout.fragment_home_work) {

    private val viewModel by viewModels<HomeworkViewModel>()

    private var sharedDatastore = ""
    private var firstStart = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.textLiveData.observe(
            viewLifecycleOwner,
            Observer { sharedDatastore = "key: $it" })
        viewModel.firstLiveData.observe(
            viewLifecycleOwner,
            Observer {
                firstStart = it
                downloadFirst()
            })
        viewModel.onError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "Ошибка: $it", Toast.LENGTH_SHORT).show()
        })
        viewModel.onProgress.observe(viewLifecycleOwner, Observer {
            //статус загрузки
            if (it!!) {
                groupElement.visibility = View.GONE
                progressBarHome.visibility = View.VISIBLE
            } else {
                groupElement.visibility = View.VISIBLE
                progressBarHome.visibility = View.GONE
            }
        })
        btDownload.setOnClickListener {
            downloadFile()
        }

    }

    private fun downloadFirst() {
        if (firstStart) return
        val urlList = mutableListOf<String>()
        lifecycleScope.launch(Dispatchers.IO) {
            resources.assets.open("urls.txt")
                .bufferedReader()
                .useLines { lines -> lines.forEach { urlList.add(it) } }
            urlList.forEach {
                downloadFile(it)
            }
            viewModel.saveFirst(true)
        }
    }

    private fun downloadFile() {
        lifecycleScope.launch(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@launch
            val textUri = etUri.text.toString()
            if (textUri.isEmpty()) {
                lifecycleScope.launch(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Введите Url для скачивания файла",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@launch
            }
            val customName =
                System.currentTimeMillis().toString() + "_" + Paths.get(textUri).fileName.toString()
            if (sharedDatastore.contains(Paths.get(textUri).fileName.toString())) {
                lifecycleScope.launch(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Файл был ранее скачан",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@launch
            }
            val testFolder = requireContext().getExternalFilesDir("files")
            val testFile: File = File(testFolder, customName)
            try {
                val result = viewModel.downloadFile(testFile, textUri)
                if (result) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "Файл $customName успешно загружен",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    viewModel.save(testFile.absolutePath)
                }
            } catch (t: Throwable) {
                Log.e("homeWork", t.localizedMessage)
                //tProgress.text = "Ошибка: " + t.localizedMessage
                testFile.delete()
            }
        }
    }

    private fun downloadFile(url: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@launch
            if (url.isEmpty()) {
                lifecycleScope.launch(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Введите Url для скачивания файла",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@launch
            }
            val customName =
                System.currentTimeMillis().toString() + "_" + Paths.get(url).fileName.toString()
            if (sharedDatastore.contains(Paths.get(url).fileName.toString())) {
                lifecycleScope.launch(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Файл был ранее скачан",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@launch
            }
            val testFolder = requireContext().getExternalFilesDir("files")
            val testFile: File = File(testFolder, customName)
            try {
                val result = viewModel.downloadFile(testFile, url)
                if (result) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "Файл $customName успешно загружен",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    viewModel.save(testFile.absolutePath)
                }
            } catch (t: Throwable) {
                Log.e("homeWork", t.localizedMessage)
                //tProgress.text = "Ошибка: " + t.localizedMessage
                testFile.delete()
            }
        }
    }
}