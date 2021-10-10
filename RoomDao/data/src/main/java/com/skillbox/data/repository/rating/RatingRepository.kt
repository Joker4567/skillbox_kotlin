package com.skillbox.data.repository.rating

import com.skillbox.data.db.entities.Rating
import com.skillbox.data.db.entities.RatingWithRestaurant

interface RatingRepository {
    suspend fun addRating(ratingItem: Rating)
    suspend fun getRatingAll(): List<RatingWithRestaurant>
    suspend fun clear()
}