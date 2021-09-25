package com.prognozrnm.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.prognozrnm.data.db.entities.ItemsDaoEntity
import com.prognozrnm.data.db.entities.ObjectItemDaoEntity
import com.prognozrnm.utils.ext.fromJson

class UserAssignedMapConverter {

    @TypeConverter
    fun fromObjectItemDaoEntity(map: Map<String, ObjectItemDaoEntity>?): String? =
        Gson().toJson(map.orEmpty())

    @TypeConverter
    fun toObjectItemDaoEntity(item: String?) =
        Gson().fromJson<Map<String, ObjectItemDaoEntity>?>(item.toString())
}

class UserAssignedListConverter {

    @TypeConverter
    fun fromRealty(list: List<ItemsDaoEntity>?): String? =
        Gson().toJson(list.orEmpty())

    @TypeConverter
    fun toRealty(item: String?) =
        Gson().fromJson<List<ItemsDaoEntity>?>(item.toString())
}