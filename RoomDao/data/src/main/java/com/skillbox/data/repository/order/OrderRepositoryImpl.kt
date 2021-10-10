package com.skillbox.data.repository.order

import com.skillbox.data.db.dao.OrderDao
import com.skillbox.data.db.entities.*

class OrderRepositoryImpl(
        private val orderDao: OrderDao
) : OrderRepository {
    override suspend fun addOrder(order: Order) {
        orderDao.addOrder(order)
    }

    override suspend fun getOrder(): List<OrderWithRestaurant> =
            orderDao.getOrder()

    override suspend fun getOrderToDish(orderId: Int) = orderDao.getOrderWithDish(orderId)

    override suspend fun clear() {
        orderDao.clear()
    }
}