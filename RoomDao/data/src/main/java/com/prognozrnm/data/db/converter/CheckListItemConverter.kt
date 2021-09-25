package com.prognozrnm.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prognozrnm.data.db.entities.CheckListDaoEntity
import com.prognozrnm.data.db.entities.CheckListItemDaoEntity
import com.prognozrnm.data.entity.CheckList
import com.prognozrnm.data.entity.CheckListItem
import com.prognozrnm.utils.ext.fromJson
import java.lang.reflect.Type
import java.util.*


class CheckListItemConverter {
    @TypeConverter
    fun fromCheckListItemDaoEntity(list: List<CheckListItemDaoEntity>?): String? =
        Gson().toJson(list.orEmpty())

    @TypeConverter
    fun toCheckListItemDaoEntity(item: String?) =
        Gson().fromJson<List<CheckListItemDaoEntity>?>(item.toString())
}