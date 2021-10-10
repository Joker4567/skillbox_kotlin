package com.skillbox.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.data.db.contract.OrderContract
import com.skillbox.data.db.contract.RestaurantContract

data class OrderWithRestaurant(
        @Embedded val restaurant: Restaurant,
        @Relation(
                parentColumn = RestaurantContract.Column.id,
                entityColumn = OrderContract.Column.restaurantId
        ) val order: List<Order>
)
