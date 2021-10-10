package com.skillbox.data.db.entities

import androidx.room.*
import com.skillbox.data.db.contract.DishContract

@Entity(tableName = DishContract.tableName)
data class Dish(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = DishContract.Column.dishId)
        val dishId: Int,
        @ColumnInfo(name = DishContract.Column.title)
        val title: String,
        @ColumnInfo(name = DishContract.Column.price)
        val price: Float,
        @ColumnInfo(name = DishContract.Column.weight)
        val weight: Float
)