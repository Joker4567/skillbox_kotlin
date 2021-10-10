package com.skillbox.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.data.db.contract.RatingContract
import com.skillbox.data.db.contract.RestaurantContract

data class RatingWithRestaurant(
        @Embedded val restaurant: Restaurant,
        @Relation(
                parentColumn = RestaurantContract.Column.id,
                entityColumn = RatingContract.Column.restaurantId
        ) val ratings: List<Rating>
)
