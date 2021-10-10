package com.skillbox.data.db.dao

import androidx.room.*
import com.skillbox.data.db.contract.DishContract
import com.skillbox.data.db.contract.MenuContract
import com.skillbox.data.db.contract.RestaurantContract
import com.skillbox.data.db.entities.Dish
import com.skillbox.data.db.entities.DishToOrder
import com.skillbox.data.db.entities.Menu
import com.skillbox.data.db.entities.MenuWithRestaurant

@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMenu(user: Menu)

    @Transaction
    @Query("SELECT * FROM ${RestaurantContract.tableName} WHERE ${RestaurantContract.Column.id} = :restaurantId")
    suspend fun getMenuRestaurantId(restaurantId: Int): List<MenuWithRestaurant>

    @Query("DELETE FROM ${MenuContract.tableName}")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDish(dish: Dish)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDishToOrder(dishToOrder: DishToOrder)

    @Query("DELETE FROM ${DishContract.tableName}")
    suspend fun clearDish()
}