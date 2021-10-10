package com.skillbox.data.db.entities

import androidx.room.*
import com.skillbox.data.db.contract.TapeContract

@Entity(tableName = TapeContract.tableName)
data class Tape(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = TapeContract.Column.id)
        val id: Int,
        @ColumnInfo(name = TapeContract.Column.title)
        val title: String,
        @ColumnInfo(name = TapeContract.Column.description)
        val description: String,
        @ColumnInfo(name = TapeContract.Column.restaurantId)
        val restaurantId: Int,
        @ColumnInfo(name = TapeContract.Column.dishId)
        val dishId: Int
)
