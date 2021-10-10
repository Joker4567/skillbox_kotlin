package com.skillbox.data.db.dao

import androidx.room.*
import com.skillbox.data.db.contract.*
import com.skillbox.data.db.entities.*

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRestaurant(restaurant: Restaurant)

    @Query("SELECT * FROM ${RestaurantContract.tableName}")
    suspend fun getRestaurantList() : List<Restaurant>

    @Update
    suspend fun updateRestaurant(restaurant: Restaurant)

    @Query("DELETE FROM ${RestaurantContract.tableName}")
    suspend fun clear()
}