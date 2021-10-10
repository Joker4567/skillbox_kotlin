package com.skillbox.data.repository.order

import com.skillbox.data.db.entities.*

interface OrderRepository {
    suspend fun addOrder(order:Order)
    suspend fun getOrder() : List<OrderWithRestaurant>
    suspend fun clear()

    suspend fun getOrderToDish(orderId: Int) : List<OrderWithDish>
}