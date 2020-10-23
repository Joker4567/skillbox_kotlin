package com.skillbox.multithreading.adapter

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.multithreading.R
import com.skillbox.multithreading.Utils.inflate
import com.skillbox.multithreading.adapter.MovieClassicAdapterDelegate.*
import com.skillbox.multithreading.networking.Movie

class MovieClassicAdapterDelegate(): AbsListItemAdapterDelegate<Movie, Movie, MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): MovieHolder {
        return MovieHolder(
            parent.inflate(R.layout.movie_item)
        )
    }

    override fun isForViewType(item: Movie, items: MutableList<Movie>, position: Int): Boolean {
        return item is Movie
    }

    override fun onBindViewHolder(
        item: Movie,
        holder: MovieHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class MovieHolder(view: View): BaseMovietAdapter(view) {

        fun bind(model:Movie) { bindMainInfo(model) }
    }
}