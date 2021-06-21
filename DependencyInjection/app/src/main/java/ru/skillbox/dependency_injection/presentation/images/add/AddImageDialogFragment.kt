package ru.skillbox.dependency_injection.presentation.images.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.skillbox.dependency_injection.databinding.DialogAddImageBinding
import ru.skillbox.dependency_injection.utils.toast

@AndroidEntryPoint
class AddImageDialogFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<AddImageViewModel>()

    private var _binding: DialogAddImageBinding? = null

    private val binding: DialogAddImageBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogAddImageBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindViewModel() {
        binding.saveButton.setOnClickListener {
            val url = binding.urlTextField.editText?.text?.toString().orEmpty()
            val name = binding.nameTextField.editText?.text?.toString().orEmpty()
            viewModel.saveImage(name, url)
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner, ::setLoading)
        viewModel.toastLiveData.observe(viewLifecycleOwner) { toast(it) }
        viewModel.saveSuccessLiveData.observe(viewLifecycleOwner) { dismiss() }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.contentGroup.isVisible = isLoading.not()
    }
}