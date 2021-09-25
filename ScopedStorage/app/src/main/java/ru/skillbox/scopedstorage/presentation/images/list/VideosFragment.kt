package ru.skillbox.scopedstorage.presentation.images.list

import android.Manifest
import android.app.Activity
import android.app.RemoteAction
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.skillbox.scopedstorage.databinding.FragmentVideoListBinding
import ru.skillbox.scopedstorage.utils.ViewBindingFragment
import ru.skillbox.scopedstorage.utils.autoCleared
import ru.skillbox.scopedstorage.utils.haveQ
import ru.skillbox.scopedstorage.utils.toast

class VideosFragment :
    ViewBindingFragment<FragmentVideoListBinding>(FragmentVideoListBinding::inflate) {

    private val viewModel: VideosListViewModel by viewModels()
    private var videosAdapter: VideosAdapter by autoCleared()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var recoverableActionLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionResultListener()
        initRecoverableActionListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initCallbacks()
        bindViewModel()
        if (hasPermission().not()) {
            requestPermissions()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updatePermissionState(hasPermission())
    }

    private fun initList() {
        videosAdapter = VideosAdapter(viewModel::deleteVideo)
        with(binding.videosList) {
            setHasFixedSize(true)
            adapter = videosAdapter
        }
    }

    private fun initCallbacks() {
        binding.addVideoButton.setOnClickListener {
            findNavController().navigate(VideosFragmentDirections.actionImagesFragmentToAddImageDialogFragment())
        }
        binding.grantPermissionButton.setOnClickListener {
            requestPermissions()
        }
    }

    private fun bindViewModel() {
        viewModel.toastLiveData.observe(viewLifecycleOwner) { toast(it) }
        viewModel.videosLiveData.observe(viewLifecycleOwner) { videosAdapter.items = it }
        viewModel.permissionsGrantedLiveData.observe(viewLifecycleOwner, ::updatePermissionUi)
        viewModel.recoverableActionLiveData.observe(viewLifecycleOwner, ::handleRecoverableAction)
    }

    private fun updatePermissionUi(isGranted: Boolean) {
        binding.grantPermissionButton.isVisible = isGranted.not()
        binding.addVideoButton.isVisible = isGranted
        binding.videosList.isVisible = isGranted
    }

    private fun hasPermission(): Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun initPermissionResultListener() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionToGrantedMap: Map<String, Boolean> ->
            if (permissionToGrantedMap.values.all { it }) {
                viewModel.permissionsGranted()
            } else {
                viewModel.permissionsDenied()
            }
        }
    }

    private fun initRecoverableActionListener() {
        recoverableActionLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) {  activityResult ->
            val isConfirmed = activityResult.resultCode == Activity.RESULT_OK
            if(isConfirmed) {
                viewModel.confirmDelete()
            } else {
                viewModel.declineDelete()
            }
        }
    }


    private fun requestPermissions() {
        requestPermissionLauncher.launch(*PERMISSIONS.toTypedArray())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleRecoverableAction(action: RemoteAction) {
        val request = IntentSenderRequest.Builder(action.actionIntent.intentSender)
            .build()
        recoverableActionLauncher.launch(request)
    }

    companion object {
        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { haveQ().not() }
        )
    }
}