package com.skillbox.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.data.db.contract.RestaurantContract
import com.skillbox.data.db.contract.TapeContract

data class TapeWithDishAndRestaurant(
        @Embedded
        val restaurant: Restaurant,
        @Relation(
                parentColumn = RestaurantContract.Column.id,
                entityColumn = TapeContract.Column.restaurantId
        )
        val tapes:List<Tape>
)
