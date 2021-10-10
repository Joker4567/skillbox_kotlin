package com.skillbox.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.skillbox.data.db.entities.TypeComponent
import com.skillbox.utils.ext.fromJson


class TypeComponentConverter {
    @TypeConverter
    fun fromTypeComponent(typeComponent: TypeComponent): String? =
        Gson().toJson(typeComponent)

    @TypeConverter
    fun toTypeComponent(item: String?) =
        Gson().fromJson<TypeComponent>(item.toString())
}