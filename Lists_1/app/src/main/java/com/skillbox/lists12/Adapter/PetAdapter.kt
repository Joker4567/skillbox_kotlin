package com.skillbox.lists12.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.lists12.Models.Pet
import com.skillbox.lists12.R
import com.skillbox.lists12.Service.inflate

class PetAdapter(
    private val onItemClick: (position: Int) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var pets: List<Pet> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_DOG -> DogHolder(parent.inflate(R.layout.item_dog), onItemClick)
            TYPE_CAT -> CatHolder(parent.inflate(R.layout.item_cat), onItemClick)
            else -> error("Incorrect viewType=$viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(pets[position]) {
            is Pet.Cat -> TYPE_CAT
            is Pet.Dog -> TYPE_DOG
        }
    }

    override fun getItemCount(): Int = pets.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is DogHolder -> {
                val person = pets[position].let { it as? Pet.Dog }
                    ?: error("Pet at position = $position is not a dog")
                holder.bind(person)
            }

            is CatHolder -> {
                val person = pets[position].let { it as? Pet.Cat }
                    ?: error("Pet at position = $position is not a cat")
                holder.bind(person)
            }

            else -> error("Incorrect view holder = $holder")
        }
    }

    fun updatePets(newPet: List<Pet>) {
        pets = newPet
    }

    abstract class BasePetHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ): RecyclerView.ViewHolder(view) {

        private val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        private val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)

        init {
            view.setOnClickListener {
                onItemClick(adapterPosition)
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

    class CatHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ): BasePetHolder(view, onItemClick) {

        private val weightTextView: TextView = view.findViewById(R.id.porodaTextView)

        fun bind(pet: Pet.Cat) {
            bindMainInfo(pet.name, pet.avatarLink)
            weightTextView.text = "Вес: ${pet.weight} кг."
        }

    }

    companion object {
        private const val TYPE_DOG = 1
        private const val TYPE_CAT = 2
    }
}