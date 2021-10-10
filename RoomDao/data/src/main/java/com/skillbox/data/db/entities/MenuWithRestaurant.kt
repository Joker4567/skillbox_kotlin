package com.skillbox.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.data.db.contract.MenuContract
import com.skillbox.data.db.contract.RestaurantContract

data class MenuWithRestaurant(
        @Embedded
        val restaurant: Restaurant,
        @Relation(
                parentColumn = RestaurantContract.Column.id,
                entityColumn = MenuContract.Column.restaurantId
        )
        val menu:List<Menu>
)