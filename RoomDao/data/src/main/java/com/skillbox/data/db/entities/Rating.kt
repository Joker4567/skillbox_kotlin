package com.skillbox.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skillbox.data.db.contract.RatingContract

@Entity(tableName = RatingContract.tableName)
data class Rating(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = RatingContract.Column.id)
        val id: Int,
        @ColumnInfo(name = RatingContract.Column.title)
        val title: String,
        @ColumnInfo(name = RatingContract.Column.description)
        val description: String,
        @ColumnInfo(name = RatingContract.Column.orderId)
        val orderId: Int,
        @ColumnInfo(name = RatingContract.Column.restaurantId)
        val restaurantId: Int,
        @ColumnInfo(name = RatingContract.Column.stars)
        val stars: Int,
)
