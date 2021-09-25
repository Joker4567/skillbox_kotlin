package ru.skillbox.services.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.skillbox.services.utils.DownloadState
import ru.skillbox.services.R
import ru.skillbox.services.service.DownloadService
import ru.skillbox.services.databinding.FragmentStartedServiceBinding

class StartedServiceFragment: Fragment(R.layout.fragment_started_service) {

    private val binding by viewBinding(FragmentStartedServiceBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startDownload.setOnClickListener {
            startService()
        }

        DownloadState.downloadState
            .onEach { isLoading ->
                binding.startDownload.isVisible = !isLoading
                binding.downloadProgress.isVisible = isLoading
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun startService() {
        val downloadIntent = Intent(requireContext(), DownloadService::class.java)
        requireContext().startService(downloadIntent)
    }

}