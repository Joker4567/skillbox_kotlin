package com.prognozrnm.data.entity

import com.prognozrnm.data.db.entities.CheckListDaoEntity
import com.prognozrnm.data.db.entities.CheckListItemDaoEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckList(
    val id:Int,
    val checkList: List<CheckListItem>
){
    fun from() = CheckListDaoEntity(
        id = id,
        checkList = checkList.map { it.from() }
    )
}