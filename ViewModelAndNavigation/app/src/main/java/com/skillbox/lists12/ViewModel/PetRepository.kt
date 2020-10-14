package com.skillbox.lists12.ViewModel

import com.skillbox.lists12.Models.Pet

class PetRepository {

    private var petsList = listOf(
        Pet.Cat(
            id = 1,
            name = "Мурзик",
            avatarLink = "https://wallbox.ru/resize/320x240/wallpapers/main2/201705/trava-koski-kotata.jpg",
            weight = 4
        ),
        Pet.Dog(
            id = 2,
            name = "Кузя",
            avatarLink = "https://i2.wp.com/sobaki-pesiki.ru/wp-content/gallery/shenki-gavayskiy-bishon/dynamic/32.jpg-nggid03387-ngg0dyn-365x230x100-00f0w010c011r110f110r010t010.jpg",
            poroda = true
        ),
        Pet.Cat(
            id = 3,
            name = "Пушистик",
            avatarLink = "https://murk.in/gallery-resize/10/10439-ab655457-murk.in.jpg",
            weight = 2
        ),
        Pet.Dog(
            id = 4,
            name = "Шарик",
            avatarLink = "https://img1.goodfon.ru/original/320x240/3/56/sobaka-schenok-leto-7432.jpg",
            poroda = false
        )
    )

    fun getPetsList() : List<Pet> = petsList

    fun createPet(pets: List<Pet>, pet:Pet): List<Pet> {
        return listOf(pet) + pets
    }

    fun deletePet(persons: List<Pet>, position: Int): List<Pet> {
        return persons.filterIndexed { index, _ -> index != position }
    }
}