package com.skillbox.data.repository.restaurant

import com.skillbox.data.db.dao.RestaurantDao
import com.skillbox.data.db.entities.Restaurant

class RestaurantRepositoryImpl(
        private val restaurantDao: RestaurantDao
) : RestaurantRepository {
    override suspend fun addRestaurant(restaurant: Restaurant) {
        restaurantDao.addRestaurant(restaurant)
    }

    override suspend fun clear() {
        restaurantDao.clear()
    }

    override suspend fun updateRestaurant(restaurant: Restaurant) {
        restaurantDao.updateRestaurant(restaurant)
    }

    override suspend fun getRestaurantList(): List<Restaurant> =
            restaurantDao.getRestaurantList()
}