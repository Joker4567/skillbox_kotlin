package com.skillbox.lists12.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.skillbox.lists12.Adapter.ImageAdapter
import com.skillbox.lists12.R
import com.skillbox.lists12.Utils.ItemOffsetDecoration
import kotlinx.android.synthetic.main.fragment_image_list.*

class ImageListFragment: Fragment(R.layout.fragment_image_list) {

    private val images = listOf(
        "https://images.unsplash.com/photo-1588064719685-bd29437024f4?ixlib=rb-1.2.1&auto=format&fit=crop&w=934&q=80",
        "https://thumbs.dreamstime.com/z/black-white-vertical-new-york-flatiron-building-stands-right-heart-manhattan-intersection-two-famous-nyc-landmarks-45486075.jpg",
        "https://images.unsplash.com/photo-1473968512647-3e447244af8f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2250&q=80",
        "https://images.unsplash.com/photo-1568198473832-b6b0f46328c1?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2192&q=80",
        "https://images.unsplash.com/photo-1580221465362-963dd392df37?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2234&q=80",
        "https://images.unsplash.com/photo-1555356342-fa18a5da123f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1600&q=80"
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
    }

    private fun initList() = with(imageList) {
        adapter = ImageAdapter().apply {
            setImages(images + images + images + images)
        }
        setHasFixedSize(true)

        addItemDecoration(ItemOffsetDecoration(requireContext()))

        layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
    }

}