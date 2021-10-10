package com.skillbox.data.db.dao

import androidx.room.*
import com.skillbox.data.db.contract.RestaurantContract
import com.skillbox.data.db.contract.TapeContract
import com.skillbox.data.db.entities.Tape
import com.skillbox.data.db.entities.TapeWithDishAndRestaurant

@Dao
interface TapeDap {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTape(tape: Tape)

    @Transaction
    @Query("SELECT * FROM ${RestaurantContract.tableName}")
    suspend fun getTape(): List<TapeWithDishAndRestaurant>

    @Query("DELETE FROM ${TapeContract.tableName}")
    suspend fun clear()
}