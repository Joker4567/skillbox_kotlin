package com.skillbox.a10_fragment_

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.skillbox.a10_fragment_.helper.ItemSelectListener
import com.skillbox.a10_fragment_.helper.MainListener


class MainFragment : Fragment(R.layout.fragment_main),
    ItemSelectListener {

    override fun onItemSelected(text: String) {
        childFragmentManager.beginTransaction()
            .add(R.id.container_fragment, DetailFragment.newInstance(text))
        .addToBackStack(text)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var checkFragment:Boolean = false
        childFragmentManager?.fragments.forEach{
            if(it is ListFragment) checkFragment = true
        }
        if(!checkFragment) {
            childFragmentManager
                .beginTransaction()
                .add(R.id.container_fragment, ListFragment())
                .addToBackStack("ListFragment")
                .commit()
        }
        (activity as? MainListener)?.onClick("[MainFragment] onCreate")
    }

    override fun onStart() {
        super.onStart()
        if(childFragmentManager?.fragments?.size == 1)
            (activity as? MainListener)?.onClick("[MainFragment] onStart")
    }

    override fun onPause() {
        super.onPause()
        (activity as? MainListener)?.onClick("[MainFragment] onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as? MainListener)?.onClick("[MainFragment] onDestroy")
    }
}