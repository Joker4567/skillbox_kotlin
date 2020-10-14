package com.skillbox.lists12.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.skillbox.lists12.App
import com.skillbox.lists12.Models.Pet
import com.skillbox.lists12.R
import kotlinx.android.synthetic.main.fragment_detail_pet.*

class DetailPetFragment : Fragment(R.layout.fragment_detail_pet) {

    private val args: DetailPetFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pet: Pet = App.activity!!.petListViewModel.getPet(args.idPet)
        if(pet is Pet.Cat){
            Glide.with(this)
                .load(pet.avatarLink)
                .placeholder(R.drawable.ic_portrait)
                .error(R.drawable.error)
                .into(imagePetDetail)
            textPetHeaderDetail.text = pet.name
        } else if(pet is Pet.Dog){
            Glide.with(this)
                .load(pet.avatarLink)
                .placeholder(R.drawable.ic_portrait)
                .error(R.drawable.error)
                .into(imagePetDetail)
            textPetHeaderDetail.text = pet.name
        }
    }
}