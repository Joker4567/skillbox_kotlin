package com.skillbox.github.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.github.models.Repositories

class RepositoriesAdapter( onItemClick: (position: Int) -> Unit ) : AsyncListDifferDelegationAdapter<Repositories>(
    UserDiffUtilCallback()
) {

    init {
        delegatesManager.addDelegate(RepositoriesAdapterDelegate(onItemClick))
    }

    class UserDiffUtilCallback : DiffUtil.ItemCallback<Repositories>() {
        override fun areItemsTheSame(oldItem: Repositories, newItem: Repositories): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repositories, newItem: Repositories): Boolean {
            return oldItem == newItem
        }
    }
}