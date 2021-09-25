package com.prognozrnm.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.prognozrnm.data.db.entities.*
import com.prognozrnm.utils.ext.fromJson

class ResultConverter {
    @TypeConverter
    fun fromObjListItemDaoEntity(list: List<ObjDaoEntity>?): String? =
        Gson().toJson(list.orEmpty())

    @TypeConverter
    fun toObjListItemDaoEntity(item: String?) =
        Gson().fromJson<List<ObjDaoEntity>?>(item.toString())

    @TypeConverter
    fun fromObjItemListItemDaoEntity(list: List<ObjItemDaoEntity>?): String? =
        Gson().toJson(list.orEmpty())

    @TypeConverter
    fun toObjItemListItemDaoEntity(item: String?) =
        Gson().fromJson<List<ObjItemDaoEntity>?>(item.toString())

    @TypeConverter
    fun fromResultItemListItemDaoEntity(list: List<ResultItemDaoEntity>?): String? =
        Gson().toJson(list.orEmpty())

    @TypeConverter
    fun toResultItemListItemDaoEntity(item: String?) =
        Gson().fromJson<List<ResultItemDaoEntity>?>(item.toString())

    @TypeConverter
    fun fromResultObjListItemDaoEntity(list: List<ResultObjDaoEntity>?): String? =
        Gson().toJson(list.orEmpty())

    @TypeConverter
    fun toResultObjListItemDaoEntity(item: String?) =
        Gson().fromJson<List<ResultObjDaoEntity>?>(item.toString())

    @TypeConverter
    fun convertStatusToString(status: ParamTypeResult): String = status.name

    @TypeConverter
    fun convertStringToStatus(statusString: String): ParamTypeResult =
        ParamTypeResult.valueOf(statusString)
}