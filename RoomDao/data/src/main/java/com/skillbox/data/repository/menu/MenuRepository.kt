package com.skillbox.data.repository.menu

import com.skillbox.data.db.entities.*

interface MenuRepository {

    suspend fun addMenu(menu: Menu)

    suspend fun getMenuRestaurantId(id: Int): List<MenuWithRestaurant>

    suspend fun clear()

    suspend fun addDish(dish: Dish)

    suspend fun clearDish()

    suspend fun addDishToOrder(dishToOrder: DishToOrder)
}