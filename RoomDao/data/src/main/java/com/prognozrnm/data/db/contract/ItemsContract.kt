package com.prognozrnm.data.db.contract

object ItemsContract {
    const val tableName = "items_table"
    object Column {
        const val id = "id"
        const val checklistType = "checklistType"
        const val name = "name"
        const val objId = "objId"
        const val resultId = "resultId"
        const val lastChange = "lastChange"
        const val params = "params"
        const val comment = "comment"
        const val userAssigned_Id = "userAssigned_Id"
    }
}