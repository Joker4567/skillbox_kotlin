package com.prognozrnm.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.prognozrnm.utils.ext.fromJson

class ListDoubleConverter {
    @TypeConverter
    fun fromDouble(list: List<Double>?): String? =
        Gson().toJson(list.orEmpty())

    @TypeConverter
    fun toDouble(item: String?) =
        Gson().fromJson<List<Double>?>(item.toString())
}