package com.skillbox.lists12.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.lists12.Models.Pet
import com.skillbox.lists12.R
import com.skillbox.lists12.Service.inflate

class DogAdapterDelegate(private val onItemClick: (position: Int) -> Unit
): AbsListItemAdapterDelegate<Pet.Dog, Pet, DogAdapterDelegate.DogHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): DogHolder {
        return DogHolder(
            parent.inflate(R.layout.item_dog),
            onItemClick
        )
    }

    override fun isForViewType(item: Pet, items: MutableList<Pet>, position: Int): Boolean {
        return item is Pet.Dog
    }

    override fun onBindViewHolder(
        item: Pet.Dog,
        holder: DogHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class DogHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ): BasePetHolder(view, onItemClick) {

        private val poroda: TextView = view.findViewById(R.id.porodaTextView)

        fun bind(pet: Pet.Dog) {
            bindMainInfo(pet.name, pet.avatarLink)
            poroda.text =  if (pet.poroda) itemView.context.resources.getString(R.string.poroda)
            else itemView.context.resources.getString(R.string.poroda_dvor)
        }
    }
}