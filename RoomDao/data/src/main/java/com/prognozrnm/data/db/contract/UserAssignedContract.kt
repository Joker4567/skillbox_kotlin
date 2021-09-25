package com.prognozrnm.data.db.contract

object UserAssignedContract {
    const val tableName = "userAssigned_table"
    object Column {
        const val id = "id"
        const val objects = "objects"
        const val items = "items"
        const val userId = "userId"
    }
}