package ru.skillbox.services.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.skillbox.services.R
import ru.skillbox.services.databinding.FragmentMenuBinding

class MenuFragment: Fragment(R.layout.fragment_menu) {

    private val binding: FragmentMenuBinding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startedServiceButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToStartedServiceFragment())
        }
        binding.workManagerButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToWorkManagerFragment())
        }
    }

}