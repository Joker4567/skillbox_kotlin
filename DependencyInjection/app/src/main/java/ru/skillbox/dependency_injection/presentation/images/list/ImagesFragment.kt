package ru.skillbox.dependency_injection.presentation.images.list

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
import dagger.hilt.android.AndroidEntryPoint
import ru.skillbox.dependency_injection.databinding.FragmentImageListBinding
import ru.skillbox.dependency_injection.utils.ViewBindingFragment
import ru.skillbox.dependency_injection.utils.autoCleared
import ru.skillbox.dependency_injection.utils.haveQ
import ru.skillbox.dependency_injection.utils.toast

@AndroidEntryPoint
class ImagesFragment :
    ViewBindingFragment<FragmentImageListBinding>(FragmentImageListBinding::inflate) {

    private val viewModel by viewModels<ImageListViewModel>()
    private var imagesAdapter: ImagesAdapter by autoCleared()

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
        imagesAdapter = ImagesAdapter(viewModel::deleteImage)
        with(binding.imagesList) {
            setHasFixedSize(true)
            adapter = imagesAdapter
        }
    }

    private fun initCallbacks() {
        binding.addImageButton.setOnClickListener {
            findNavController().navigate(ImagesFragmentDirections.actionImagesFragmentToAddImageDialogFragment())
        }
        binding.grantPermissionButton.setOnClickListener {
            requestPermissions()
        }
    }

    private fun bindViewModel() {
        viewModel.toastLiveData.observe(viewLifecycleOwner) { toast(it) }
        viewModel.imagesLiveData.observe(viewLifecycleOwner) { imagesAdapter.items = it }
        viewModel.permissionsGrantedLiveData.observe(viewLifecycleOwner, ::updatePermissionUi)
        viewModel.recoverableActionLiveData.observe(viewLifecycleOwner, ::handleRecoverableAction)
    }

    private fun updatePermissionUi(isGranted: Boolean) {
        binding.grantPermissionButton.isVisible = isGranted.not()
        binding.addImageButton.isVisible = isGranted
        binding.imagesList.isVisible = isGranted
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