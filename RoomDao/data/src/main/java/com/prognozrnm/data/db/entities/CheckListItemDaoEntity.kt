package com.prognozrnm.data.db.entities

data class CheckListItemDaoEntity(
    val id:Int,
    val name:String,
    val unit:String?,
    val inputType:Int,
    val required:Boolean,
    val readonly:Boolean,
    var resultText:String = ""
)