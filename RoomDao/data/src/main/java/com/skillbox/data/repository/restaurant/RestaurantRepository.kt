package com.skillbox.data.repository.restaurant

import com.skillbox.data.db.entities.Restaurant

interface RestaurantRepository {
    suspend fun addRestaurant(restaurant: Restaurant)
    suspend fun clear()
    suspend fun updateRestaurant(restaurant: Restaurant)
    suspend fun getRestaurantList() : List<Restaurant>
}