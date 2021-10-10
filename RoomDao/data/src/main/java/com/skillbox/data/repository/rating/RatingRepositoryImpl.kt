package com.skillbox.data.repository.rating

import com.skillbox.data.db.dao.RatingDao
import com.skillbox.data.db.entities.Rating
import com.skillbox.data.db.entities.RatingWithRestaurant

class RatingRepositoryImpl(
        private val ratingDao: RatingDao
) : RatingRepository {
    override suspend fun addRating(ratingItem: Rating) {
        ratingDao.addRating(ratingItem)
    }

    override suspend fun getRatingAll(): List<RatingWithRestaurant> =
            ratingDao.getRatingAll()

    override suspend fun clear() {
        ratingDao.clear()
    }
}