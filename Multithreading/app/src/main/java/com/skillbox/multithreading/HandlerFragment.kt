package com.skillbox.multithreading

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_handler.*
import kotlin.random.Random

class HandlerFragment : Fragment(R.layout.fragment_handler) {

    private lateinit var handler: Handler
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val backgroundThread = HandlerThread("handler thread").apply {
            start()
        }

        handler = Handler(backgroundThread.looper)

        executeButton.setOnClickListener {
            handler.post {
                Log.d("Handler", "Execute task from thread = ${Thread.currentThread().name}")
                val randomNumber = Random.nextLong()
                mainHandler.post {
                    Log.d("Handler", "Execute view task from thread = ${Thread.currentThread().name}")
                    textView.text = randomNumber.toString()
                }

                mainHandler.postDelayed({
                    Toast.makeText(requireContext(), "Generated number = $randomNumber", Toast.LENGTH_SHORT).show()
                }, 500)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.looper.quit()
    }

}