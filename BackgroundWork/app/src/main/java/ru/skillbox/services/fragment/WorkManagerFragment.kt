package ru.skillbox.services.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.skillbox.services.service.DownloadWorker
import ru.skillbox.services.R
import ru.skillbox.services.databinding.FragmentWorkManagerBinding
import ru.skillbox.services.utils.haveQ
import ru.skillbox.services.utils.toast
import timber.log.Timber
import java.util.concurrent.TimeUnit

class WorkManagerFragment : Fragment(R.layout.fragment_work_manager) {

    private val binding by viewBinding(FragmentWorkManagerBinding::bind)
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionResultListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        WorkManager.getInstance(requireContext())
            .getWorkInfosForUniqueWorkLiveData(DOWNLOAD_WORK_ID)
            .observe(viewLifecycleOwner, { if(startDownload) handleWorkInfo(it.first()) })

        binding.startDownload.setOnClickListener {
            startDownload()
        }

        binding.cancelDownload.setOnClickListener {
            stopDownload()
        }

        if (hasPermission().not()) {
            requestPermissions()
        }
    }

    private fun stopDownload() {
        WorkManager.getInstance(requireContext()).cancelUniqueWork(DOWNLOAD_WORK_ID)
    }

    private fun startDownload() {
        startDownload = true
        if(hasPermission().not()){
            requestPermissions()
            return
        }
        val urlToDownload = binding.downloadUrl.text.toString()
        val nameFile = binding.nameFile.text.toString()

        val workData = workDataOf(
            DownloadWorker.DOWNLOAD_URL_KEY to urlToDownload,
            DownloadWorker.NAME_FILE_KEY to nameFile
        )

        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)//Безлимитное соединение
            .setRequiresBatteryNotLow(true)//Нормальный уровень заряда
            .build()

        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(workData)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.SECONDS)
            .setConstraints(workConstraints)
            .build()

        WorkManager.getInstance(requireContext())
            .enqueueUniqueWork(DOWNLOAD_WORK_ID, ExistingWorkPolicy.KEEP, workRequest)
    }

    private fun handleWorkInfo(workInfo: WorkInfo) {
        Timber.d("handleWorkInfo new state = ${workInfo.state}")
        val isFinished = workInfo.state.isFinished
        when(workInfo.state){
            WorkInfo.State.FAILED -> {
                binding.startDownload.isVisible = true
                binding.startDownload.text = getString(R.string.fragment_work_manager_retry)
                binding.cancelDownload.isVisible = false
                binding.downloadProgress.isVisible = false
            }
            WorkInfo.State.SUCCEEDED -> {
                binding.startDownload.isVisible = true
                binding.startDownload.text = getString(R.string.fragment_work_manager_bt_download)
                binding.cancelDownload.isVisible = false
                binding.downloadProgress.isVisible = false
            }
            WorkInfo.State.RUNNING -> {
                binding.tvWait.isVisible = false
            }
            WorkInfo.State.CANCELLED -> {
                binding.startDownload.isVisible = true
                binding.cancelDownload.isVisible = false
                binding.downloadProgress.isVisible = false
                binding.tvWait.isVisible = false
            }
            else -> {
                binding.startDownload.isVisible = false
                binding.cancelDownload.isVisible = true
                binding.downloadProgress.isVisible = true
                binding.tvWait.isVisible = true
            }
        }
        if (isFinished) {
            toast("work finished with state = ${workInfo.state}")
        }
    }

    private fun hasPermission(): Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(*PERMISSIONS.toTypedArray())
    }

    private fun initPermissionResultListener() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionToGrantedMap: Map<String, Boolean> ->
            if (permissionToGrantedMap.values.all { it }) {
                //true
            } else {
                //false
            }
        }
    }

    companion object {
        private const val DOWNLOAD_WORK_ID = "download work"
        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { haveQ().not() }
        )
        private var startDownload = false
    }
}