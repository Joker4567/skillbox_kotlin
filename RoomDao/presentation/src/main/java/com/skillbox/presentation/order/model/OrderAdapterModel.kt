package com.skillbox.presentation.order.model

data class OrderAdapterModel(
        val orderId: Int,
        val restaurant: String,
        val discount: Int,
        val date: String,
        val price: Float,
        val stars: Int
)
