package com.skillbox.data.db.contract

object TapeContract {
    const val tableName = "tape"
    object Column {
        const val title = "TapeTitle"
        const val id = "TapeId"
        const val description = "TapeDescription"
        const val dishId = "TapeDishId"
        const val restaurantId = "TapeRestaurantId"
    }
}