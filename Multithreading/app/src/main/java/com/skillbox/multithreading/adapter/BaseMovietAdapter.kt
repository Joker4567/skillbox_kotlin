package com.skillbox.multithreading.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.multithreading.R
import com.skillbox.multithreading.networking.Movie

abstract class BaseMovietAdapter(
    view: View
): RecyclerView.ViewHolder(view) {

    private val nameTextView: TextView = view.findViewById(R.id.nameFile)
    private val filmYearTextView: TextView = view.findViewById(R.id.yearFilm)

    protected fun bindMainInfo(
        model: Movie
    ) {
        nameTextView.text = model.title
        filmYearTextView.text = model.year.toString() + " г."
    }
}