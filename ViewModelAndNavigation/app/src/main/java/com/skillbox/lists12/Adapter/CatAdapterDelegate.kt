package com.skillbox.lists12.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.lists12.Models.Pet
import com.skillbox.lists12.R
import com.skillbox.lists12.Utils.inflate

class CatAdapterDelegate(
    private val onRemoveItemClick: (position: Int) -> Unit,
    private val onItemClick: (position: Int) -> Unit
): AbsListItemAdapterDelegate<Pet.Cat, Pet, CatAdapterDelegate.CatHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): CatHolder {
        return CatHolder(
            parent.inflate(R.layout.item_cat),
            onRemoveItemClick,
            onItemClick
        )
    }

    override fun isForViewType(item: Pet, items: MutableList<Pet>, position: Int): Boolean {
        return item is Pet.Cat
    }

    override fun onBindViewHolder(
        item: Pet.Cat,
        holder: CatHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class CatHolder(
        view: View,
        onRemoveItemClick: (position: Int) -> Unit,
        onItemClick: (position: Int) -> Unit
    ): BasePetHolder(view, onRemoveItemClick, onItemClick) {

        private val weightTextView: TextView = view.findViewById(R.id.porodaTextView)

        fun bind(pet: Pet.Cat) {
            bindMainInfo(pet.name, pet.avatarLink)
            weightTextView.text = "Вес: ${pet.weight} кг."
        }
    }
}