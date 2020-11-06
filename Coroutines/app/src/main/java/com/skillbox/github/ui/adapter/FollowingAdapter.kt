package com.skillbox.github.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.github.models.Following

class FollowingAdapter() : AsyncListDifferDelegationAdapter<Following>(
    UserDiffUtilCallback()
) {

    init {
        delegatesManager.addDelegate(FollowingAdapterDelegate())
    }

    class UserDiffUtilCallback : DiffUtil.ItemCallback<Following>() {
        override fun areItemsTheSame(oldItem: Following, newItem: Following): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Following, newItem: Following): Boolean {
            return oldItem == newItem
        }
    }
}