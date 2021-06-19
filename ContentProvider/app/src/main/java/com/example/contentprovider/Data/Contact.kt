package com.example.contentprovider.Data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Contact(
    override val id: Long,
    override val name: String,
    override val numbers: List<String>,
    override val avatar: String
) : BaseContact(id, name, numbers, avatar),
    Parcelable
