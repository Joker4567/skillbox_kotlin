package com.prognozrnm.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.prognozrnm.utils.ext.fromJson

class ListIntConverter {
    @TypeConverter
    fun fromInt(list: List<Int>?): String? =
        Gson().toJson(list.orEmpty())

    @TypeConverter
    fun toInt(item: String?) =
        Gson().fromJson<List<Int>?>(item.toString())
}