package com.skillbox.data.db.contract

object MenuContract {
    const val tableName = "menu"

    object Column {
        const val title = "title"
        const val description = "description"
        const val id = "id"
        const val restaurantId = "restaurantId"
    }
}