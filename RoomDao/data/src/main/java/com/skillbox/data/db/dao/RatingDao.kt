package com.skillbox.data.db.dao

import androidx.room.*
import com.skillbox.data.db.contract.RatingContract
import com.skillbox.data.db.contract.RestaurantContract
import com.skillbox.data.db.entities.Rating
import com.skillbox.data.db.entities.RatingWithRestaurant

@Dao
interface RatingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRating(ratingItem: Rating)

    @Transaction
    @Query("SELECT * FROM ${RestaurantContract.tableName}")
    suspend fun getRatingAll(): List<RatingWithRestaurant>

    @Query("DELETE FROM ${RatingContract.tableName}")
    suspend fun clear()
}