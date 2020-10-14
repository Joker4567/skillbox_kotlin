package com.skillbox.lists12.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.lists12.Models.Pet

class PetAdapter(
    onRemoveItemClick: (position: Int) -> Unit,
    onItemClick: (position: Int) -> Unit
) : AsyncListDifferDelegationAdapter<Pet>(PersonDiffUtilCallback()) {
    init {
        delegatesManager
            .addDelegate(CatAdapterDelegate(onRemoveItemClick, onItemClick))
            .addDelegate(DogAdapterDelegate(onRemoveItemClick, onItemClick))
    }

    class PersonDiffUtilCallback : DiffUtil.ItemCallback<Pet>() {
        override fun areItemsTheSame(oldItem: Pet, newItem: Pet): Boolean {
            return when {
                oldItem is Pet.Cat && newItem is Pet.Cat -> oldItem.id == newItem.id
                oldItem is Pet.Dog && newItem is Pet.Dog -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Pet, newItem: Pet): Boolean {
            return oldItem == newItem
        }
    }
}