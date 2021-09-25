package com.prognozrnm.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorkList(
    val works: List<Work>
) : Parcelable {
    @Parcelize
    data class Work(
        val nameObj:String,
        val itemName:String,
        val type:Byte,
        val params:List<Int>,
        val comment:String = "",
        val resultId:Int,
        val objId:String,
        val checklistType:Int
    ) : Parcelable
}