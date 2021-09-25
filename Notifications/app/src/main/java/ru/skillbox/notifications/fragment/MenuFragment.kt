package ru.skillbox.notifications.fragment

import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.skillbox.notifications.R
import ru.skillbox.notifications.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding: FragmentMenuBinding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.firebaseButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToFirebaseFragment())
        }
        binding.syncButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToSyncFragment())
        }
        NotificationManagerCompat.from(requireContext()).cancelAll()
    }
}