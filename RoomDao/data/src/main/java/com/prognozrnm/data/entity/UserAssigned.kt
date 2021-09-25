package com.prognozrnm.data.entity

import com.prognozrnm.data.db.entities.ItemsDaoEntity
import com.prognozrnm.data.db.entities.ObjectItemDaoEntity
import com.prognozrnm.data.db.entities.UserAssignedDaoEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAssigned(
    @Json(name = "objects")
    val objects:Map<String, ObjectItem>,
    @Json(name = "items")
    val items:List<Items>
){
    fun from(userId:String):UserAssignedDaoEntity = UserAssignedDaoEntity(
        id = 0,
        userId = userId,
        objects = objects.map { it.key to it.value.from() }.toMap(),
        items = items.map { it.from() }
    )
}
@JsonClass(generateAdapter = true)
data class ObjectItem(
    @Json(name = "type")
    val type:String,
    @Json(name = "name")
    val name:String,
    @Json(name = "location")
    val location:List<Double> = emptyList()
) {
    fun from():ObjectItemDaoEntity = ObjectItemDaoEntity(
        id = 0, type, name, location
    )
}
@JsonClass(generateAdapter = true)
data class Items(
    @Json(name = "checklistType")
    val checklistType:Int,
    @Json(name = "name")
    val name:String,
    @Json(name = "objId")
    val objId:String,
    @Json(name = "resultId")
    val resultId:Int,
    @Json(name = "lastChange")
    val lastChange:Double,
    @Json(name = "params")
    val params:List<Int> = emptyList(),
    @Json(name = "comment")
    val comment:String = ""
){
    fun from() : ItemsDaoEntity = ItemsDaoEntity(
        id = 0, checklistType, name, objId, resultId, lastChange, params, comment
    )
}
