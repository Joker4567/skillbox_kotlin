package com.skillbox.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skillbox.data.db.contract.OrderContract

@Entity(tableName = OrderContract.tableName)
data class Order(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = OrderContract.Column.orderId)
        val orderId: Int,
        @ColumnInfo(name = OrderContract.Column.restaurantId)
        val restaurantId: Int,
        @ColumnInfo(name = OrderContract.Column.payDate)
        val payDate: String,
        @ColumnInfo(name = OrderContract.Column.createOrder)
        val createOrder: String,
        @ColumnInfo(name = OrderContract.Column.priceOrder)
        val priceOrder: Float,
        @ColumnInfo(name = OrderContract.Column.discount)
        val discount: Int
)