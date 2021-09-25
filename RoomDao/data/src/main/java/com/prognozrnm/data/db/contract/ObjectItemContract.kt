package com.prognozrnm.data.db.contract

object ObjectItemContract {
    const val tableName = "objectItem_table"
    object Column {
        const val id = "id"
        const val type = "type"
        const val name = "name"
        const val location = "location"
        const val userAssigned_Id = "userAssigned_Id"
    }
}