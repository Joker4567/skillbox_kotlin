package com.skillbox.lists12.Service

import android.os.Parcelable
import com.skillbox.lists12.Models.Pet
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
internal data class FormState(
    var petsList:@RawValue MutableList<Pet> = emptyList<Pet>().toMutableList()
): Parcelable {

    fun save(petsAdapter:MutableList<Pet>): FormState {
        this.petsList = petsAdapter
        return copy(petsList = petsAdapter)
    }
}