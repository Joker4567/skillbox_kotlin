package com.skillbox.multithreading

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: Fragment(R.layout.fragment_main) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        threading.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_threadingFragment)
        }
        deadlock.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_deadlockFragment)
        }
        raceCondition.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_raceConditionFragment)
        }
        livelock.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_livelockFragment)
        }
        handler.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_handlerFragment)
        }
    }
}