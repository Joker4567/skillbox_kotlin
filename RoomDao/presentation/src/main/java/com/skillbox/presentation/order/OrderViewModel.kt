package com.skillbox.presentation.order

import androidx.lifecycle.MutableLiveData
import com.skillbox.data.repository.order.OrderRepository
import com.skillbox.presentation.order.model.OrderAdapterModel
import com.skillbox.utils.platform.BaseViewModel

class OrderViewModel(
        private val orderRepository: OrderRepository
) : BaseViewModel() {

    val source = MutableLiveData<List<OrderAdapterModel>>()

    fun getOrder() {
        launchIO {
            val res = orderRepository.getOrder()
            launch {
                if (res.isNotEmpty()) {
                    val itemListOrder = emptyList<OrderAdapterModel>().toMutableList()
                    res.forEach { restaurant ->
                        restaurant.order.forEach { order ->
                            val item = OrderAdapterModel(
                                    restaurant = restaurant.restaurant.name,
                                    discount = order.discount,
                                    date = order.payDate,
                                    price = order.priceOrder,
                                    stars = order.discount,
                                    orderId = order.orderId
                            )
                            itemListOrder.add(item)
                        }
                    }
                    source.postValue(itemListOrder)
                }
            }
        }
    }

}