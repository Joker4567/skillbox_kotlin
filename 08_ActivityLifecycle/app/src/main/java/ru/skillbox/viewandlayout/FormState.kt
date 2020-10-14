package ru.skillbox.viewandlayout

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class FormState(
    var email: String,
    var password: String,
    var checkAgree:Boolean
): Parcelable {

    fun save(email:String, password:String, checkAgree:Boolean): FormState {
        this.email = email
        this.password = password
        this.checkAgree = checkAgree
        return copy(email = email, password = password, checkAgree = checkAgree)
    }
}