package com.skillbox.data.db.dao

import androidx.room.*
import com.skillbox.data.db.contract.DishContract
import com.skillbox.data.db.contract.OrderContract
import com.skillbox.data.db.contract.RestaurantContract
import com.skillbox.data.db.entities.*

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrder(orderItem: Order)

    @Transaction
    @Query("SELECT * FROM ${RestaurantContract.tableName}")
    suspend fun getOrder(): List<OrderWithRestaurant>

    @Transaction
    @Query("SELECT * FROM ${DishContract.tableName}")
    suspend fun getDishWithOrder(): List<DishWithOrder>

    @Transaction
    @Query("SELECT * FROM ${OrderContract.tableName} WHERE orderId = :orderId")
    suspend fun getOrderWithDish(orderId: Int): List<OrderWithDish>

    @Query("DELETE FROM ${OrderContract.tableName}")
    suspend fun clear()
}