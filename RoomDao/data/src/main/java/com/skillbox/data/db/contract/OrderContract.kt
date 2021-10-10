package com.skillbox.data.db.contract

object OrderContract {
    const val tableName = "orderTable"
    object Column {
        const val orderId = "orderId"
        const val restaurantId = "OrderRestaurantId"
        const val payDate = "OrderPayDate"
        const val priceOrder = "OrderPriceOrder"
        const val createOrder = "OrderCreateOrder"
        const val discount = "discount"
    }
}