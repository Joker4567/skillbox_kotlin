package com.skillbox.multithreading.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.multithreading.networking.Movie

class MovieAdapter() : AsyncListDifferDelegationAdapter<Movie>(PersonDiffUtilCallback()) {
    init {
        delegatesManager
            .addDelegate(MovieClassicAdapterDelegate())
    }

    class PersonDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            //id not json api server movie film
            return when {
                oldItem is Movie && newItem is Movie -> oldItem.title == newItem.title
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}