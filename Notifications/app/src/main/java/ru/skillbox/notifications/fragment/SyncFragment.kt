package ru.skillbox.notifications.fragment

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.skillbox.notifications.R
import ru.skillbox.notifications.databinding.FragmentSyncBinding
import ru.skillbox.notifications.service.NetworkReceiver
import ru.skillbox.notifications.utils.NotificationChannels
import ru.skillbox.notifications.utils.isOnline

class SyncFragment : Fragment(R.layout.fragment_sync) {

    private val binding: FragmentSyncBinding by viewBinding(FragmentSyncBinding::bind)

    private val receiver = NetworkReceiver()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btSync.setOnClickListener {
            if(requireContext().isOnline().not())
                Toast.makeText(requireContext(), "Включите передачу данных WiFi or 3G", Toast.LENGTH_SHORT).show()
            else {
                binding.syncProgressBar.visibility = View.VISIBLE
                binding.btSync.isEnabled = false
                showProgressNotification()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filters = IntentFilter()
        filters.addAction("android.net.wifi.WIFI_STATE_CHANGED")
        filters.addAction("android.net.wifi.STATE_CHANGE")
        filters.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        requireContext().registerReceiver(receiver, filters)
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(receiver)
    }

    private fun showProgressNotification() {
        val notificationBuilder = NotificationCompat.Builder(
            requireContext(),
            NotificationChannels.SALE_CHANNEL_ID
        )
            .setContentTitle("Sync")
            .setContentText("Sync in progress")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_notifications)

        val maxProgress = 10
        binding.syncProgressBar.max = maxProgress
        binding.syncProgressBar.progress = 0
        lifecycleScope.launch {
            (0 until maxProgress).forEach { progress ->
                val notification = notificationBuilder
                    .setProgress(maxProgress, progress, false)
                    .build()

                binding.syncProgressBar.progress = progress

                NotificationManagerCompat.from(requireContext())
                    .notify(PROGRESS_NOTIFICATION_ID, notification)

                delay(500)
            }

            val finalNotification = notificationBuilder
                .setContentText("Sync is completed")
                .setProgress(0, 0, false)
                .build()

            NotificationManagerCompat.from(requireContext())
                .notify(PROGRESS_NOTIFICATION_ID, finalNotification)
            delay(1000)

            NotificationManagerCompat.from(requireContext())
                .cancel(PROGRESS_NOTIFICATION_ID)

            binding.syncProgressBar.visibility = View.GONE
            binding.btSync.isEnabled = true
        }

    }

    companion object {
        private const val PROGRESS_NOTIFICATION_ID = 22345
    }
}