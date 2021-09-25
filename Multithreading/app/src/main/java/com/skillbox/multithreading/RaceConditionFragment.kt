package com.skillbox.multithreading

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_condition.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.logging.Handler
import kotlin.time.TimeSource

class RaceConditionFragment: Fragment(R.layout.fragment_condition) {

    private var value: Int = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btNotSynchronized.setOnClickListener {
            if(valid()) {
                makeMultithreadingIIncrement(false)
            }
            else
                Toast.makeText(requireContext(), "Введите числа!",Toast.LENGTH_SHORT).show()
        }
        btSynchronized.setOnClickListener {
            if(valid()) {
                makeMultithreadingIIncrement(true)
            }
            else
                Toast.makeText(requireContext(), "Введите числа!",Toast.LENGTH_SHORT).show()
        }
    }

    private fun makeMultithreadingIIncrement(flag: Boolean) {
        view?.clearFocus();
        value = 0;
        val threadCount = etThread.text.toString().toInt()
        val incrementCount = etIncrement.text.toString().toInt()
        val expectedValue = value + threadCount * incrementCount
        var time_start = System.currentTimeMillis() //Current time second
        btNotSynchronized.isEnabled = false
        btSynchronized.isEnabled = false
        (0 until threadCount).map {
            Thread {
                if(flag) {
                    synchronized(this) {
                        body(incrementCount)
                    }
                } else {
                    body(incrementCount)
                }
            }.apply {
                start()
            }
        }.map { it.join() }
        var time_end = System.currentTimeMillis() //Current time second
        tvResult.text = "value=$value, expected=$expectedValue" + "\nTime: ${time_end-time_start} milli_sec"
        btNotSynchronized.isEnabled = true
        btSynchronized.isEnabled = true
    }

    private fun body(incrementCount:Int){
        for (i in 0 until incrementCount) {
            value++
        }
    }

    private fun valid():Boolean = etThread.text.isNotEmpty() && etIncrement.text.isNotEmpty()
}