package com.skillbox.networking.movie_list

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MovieListAdapter: AsyncListDifferDelegationAdapter<RemoteMovie>(MovieDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(MovieDelegateAdapter())
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<RemoteMovie>() {
        override fun areItemsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem == newItem
        }
    }

}