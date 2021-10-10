package com.skillbox.presentation.order_datail

import androidx.lifecycle.MutableLiveData
import com.skillbox.data.db.entities.Menu
import com.skillbox.data.repository.order.OrderRepository
import com.skillbox.presentation.order.model.OrderAdapterModel
import com.skillbox.utils.platform.BaseViewModel

class OrderDetailViewModel(
        private val orderRepository: OrderRepository
) : BaseViewModel() {
    val source = MutableLiveData<List<Menu>>()

    fun getDetailOrder(orderId: Int) {
        launchIO {
            val res = orderRepository.getOrderToDish(orderId)
            launch {
                val itemMenu = emptyList<Menu>().toMutableList()
                if(res.isNotEmpty()) {
                    res.forEach {
                        if(it.order.orderId == orderId) {
                            it.dish.forEach { dish ->
                                val item = Menu(
                                        dish.dishId,
                                        it.order.restaurantId,
                                        dish.title,
                                        "Вес: ${dish.weight}, Цена: ${dish.price} руб."
                                )
                                itemMenu.add(item)
                            }
                        }
                    }
                    source.postValue(itemMenu)
                }
            }
        }
    }
}