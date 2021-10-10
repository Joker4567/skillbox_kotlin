package com.skillbox.data.repository.menu

import com.skillbox.data.db.dao.MenuDao
import com.skillbox.data.db.entities.*

class MenuRepositoryImp(
        private val menuDao: MenuDao
) : MenuRepository {

    override suspend fun addMenu(menu: Menu) {
        menuDao.addMenu(menu)
    }

    override suspend fun getMenuRestaurantId(id: Int): List<MenuWithRestaurant> {
        return menuDao.getMenuRestaurantId(id).filter { x -> x.restaurant.id == id }
    }

    override suspend fun clear() {
        menuDao.clear()
    }

    override suspend fun addDish(dish: Dish) {
        menuDao.addDish(dish)
    }

    override suspend fun clearDish() {
        menuDao.clearDish()
    }

    override suspend fun addDishToOrder(dishToOrder: DishToOrder) {
        menuDao.addDishToOrder(dishToOrder)
    }
}