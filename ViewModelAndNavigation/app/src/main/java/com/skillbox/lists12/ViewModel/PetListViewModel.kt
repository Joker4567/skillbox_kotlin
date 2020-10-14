package com.skillbox.lists12.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.lists12.Models.Pet
import com.skillbox.lists12.Utils.SingleLiveEvent

class PetListViewModel : ViewModel() {
    private val repository = PetRepository()

    private val petLiveData = MutableLiveData<List<Pet>>(
        repository.getPetsList()
    )

    private val showToastLiveData =
        SingleLiveEvent<Unit>()

    val pets: LiveData<List<Pet>>
        get() = petLiveData

    val showToast: SingleLiveEvent<Unit>
        get() = showToastLiveData

    fun addPet(pet:Pet) {
        val updatedList = repository.createPet(petLiveData.value.orEmpty(), pet)
        petLiveData.postValue(updatedList)
    }

    fun removePet(position: Int) {
        petLiveData.postValue(
            repository.deletePet(petLiveData.value.orEmpty(), position)
        )
        showToastLiveData.postValue(Unit)
    }

    fun getPet(position:Int):Pet {
        return petLiveData.value!![position]
    }
}