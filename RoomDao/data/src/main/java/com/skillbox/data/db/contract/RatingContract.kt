package com.skillbox.data.db.contract

object RatingContract {
    const val tableName = "rating"

    object Column {
        const val title = "RatingTitle"
        const val id = "RatingId"
        const val description = "RatingDescription"
        const val orderId = "RatingOrderId"
        const val restaurantId = "RatingRestaurantId"
        const val stars = "RatingStars"
    }
}