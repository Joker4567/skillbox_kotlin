package ru.skillbox.scopedstorage.presentation.docs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.skillbox.scopedstorage.R
import ru.skillbox.scopedstorage.databinding.FragmentDocsBinding
import ru.skillbox.scopedstorage.utils.ViewBindingFragment
import ru.skillbox.scopedstorage.utils.toast


class DocsFragment : ViewBindingFragment<FragmentDocsBinding>(FragmentDocsBinding::inflate) {

    private lateinit var openDocumentLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var createDocumentLauncher: ActivityResultLauncher<String>
    private lateinit var selectDocumentDirectoryLauncher: ActivityResultLauncher<Uri>
    private val viewModel: DocsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initOpenDocumentLauncher()
        initCreateDocumentLauncher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.readFileButton.setOnClickListener {
            readFile()
        }
        binding.createFileButton.setOnClickListener {
            createFile()
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner, ::setLoading)
        viewModel.toastLiveData.observe(viewLifecycleOwner) { toast(it) }
        viewModel.saveSuccessLiveData.observe(viewLifecycleOwner) { toast(getString(R.string.successDownload)) }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.readFileButton.isVisible = isLoading.not()
        binding.createFileButton.isVisible = isLoading.not()
    }

    private fun initOpenDocumentLauncher() {
        openDocumentLauncher = registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->
            handleOpenDocument(uri)
        }
    }

    private fun initCreateDocumentLauncher() {
        createDocumentLauncher = registerForActivityResult(
            ActivityResultContracts.CreateDocument()
        ) { uri ->
            handleCreateFile(uri)
        }
    }

    private fun readFile() {
        openDocumentLauncher.launch(arrayOf("video/*"))
    }

    private fun createFile() {
        createDocumentLauncher.launch("sample.mp4")
    }

    private fun handleOpenDocument(uri: Uri?) {
        if (uri == null) {
            toast("not selected")
            return
        }
        val openLinkIntent = Intent()
        openLinkIntent.action = Intent.ACTION_VIEW
        openLinkIntent.setDataAndType(uri, "video/mp4")

        if (openLinkIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(openLinkIntent)
        } else {
            toast("Не получается обработать намерение!")
        }
    }

    private fun handleCreateFile(uri: Uri?) {
        if (uri == null) {
            toast("file not created")
            return
        }
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                uri.let { viewModel.saveVideo(
                    "sample.mp4",
                    uri,
                    "https://thumbs.gfycat.com/ThunderousUnnaturalIslandwhistler-mobile.mp4"
                ) }
            }
        }
    }
}