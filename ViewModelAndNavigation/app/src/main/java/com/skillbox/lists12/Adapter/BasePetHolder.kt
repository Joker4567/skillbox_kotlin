package com.skillbox.lists12.Adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.lists12.Fragment.PetListFragmentDirections
import com.skillbox.lists12.R

abstract class BasePetHolder(
    view: View,
    onRemoveItemClick: (position: Int) -> Unit,
    onItemClick: (position: Int) -> Unit
): RecyclerView.ViewHolder(view) {

    private val nameTextView: TextView = view.findViewById(R.id.nameTextView)
    private val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)

    init {
        view.setOnLongClickListener {
            onRemoveItemClick(adapterPosition)
            true
        }
        view.setOnClickListener {
            onItemClick(adapterPosition)
            true
        }
    }

    protected fun bindMainInfo(
        name: String,
        avatarLink: String
    ) {
        nameTextView.text = name
        Glide.with(itemView)
            .load(avatarLink)
            .placeholder(R.drawable.ic_portrait)
            .error(R.drawable.error)
            .into(avatarImageView)
    }
}