package com.skillbox.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skillbox.data.db.contract.RestaurantContract

@Entity(tableName = RestaurantContract.tableName)
data class Restaurant(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = RestaurantContract.Column.id)
        val id: Int,
        @ColumnInfo(name = RestaurantContract.Column.name)
        var name: String
)
