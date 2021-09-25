package ru.skillbox.scopedstorage.presentation.images.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.skillbox.scopedstorage.databinding.DialogAddVideoBinding
import ru.skillbox.scopedstorage.utils.toast

class AddVideoDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: AddVideoViewModel by viewModels()

    private var _binding: DialogAddVideoBinding? = null

    private val binding: DialogAddVideoBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogAddVideoBinding.inflate(LayoutInflater.from(requireContext()))
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
            viewModel.saveVideo(name, url)
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