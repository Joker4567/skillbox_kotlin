package com.skillbox.data.db.contract

object DishToOrderContract {
    const val tableName = "DishToOrder"

    object Column {
        const val orderId = "orderId"
        const val dishId = "dishId"
    }
}