package com.prognozrnm.data.entity

import com.prognozrnm.data.db.entities.CheckListItemDaoEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckListItem(
    @Json(name = "Id")
    val id:Int,
    @Json(name = "GaugeName")
    val name:String,
    @Json(name = "Unit")
    val unit:String?,
    @Json(name = "InputType")
    val inputType:Int,
    @Json(name = "Required")
    val required:Boolean,
    @Json(name = "Readonly")
    val readonly:Boolean
) {
    fun from() = CheckListItemDaoEntity(
        id, name, unit, inputType, required, readonly
    )
}