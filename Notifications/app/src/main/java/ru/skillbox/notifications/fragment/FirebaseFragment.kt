package ru.skillbox.notifications.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import ru.skillbox.notifications.R
import ru.skillbox.notifications.databinding.FragmentFirebaseBinding
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseFragment : Fragment(R.layout.fragment_firebase) {

    private val binding: FragmentFirebaseBinding by viewBinding(FragmentFirebaseBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getTokenButton.setOnClickListener {
            getToken()
        }
    }

    private fun getToken() {
        lifecycleScope.launch {
            val token = getTokenSuspend()
            Timber.d("token = $token")
        }
    }

    private suspend fun getTokenSuspend(): String? = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                continuation.resume(token)
            }
            .addOnFailureListener { exception ->
                continuation.resume(null)
            }
            .addOnCanceledListener {
                continuation.resume(null)
            }
    }

}