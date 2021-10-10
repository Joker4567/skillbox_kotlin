package com.skillbox.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skillbox.data.db.contract.MenuContract

@Entity(tableName = MenuContract.tableName)
data class Menu(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = MenuContract.Column.id)
        val id: Int,
        @ColumnInfo(name = MenuContract.Column.restaurantId)
        val restaurantId: Int,
        @ColumnInfo(name = MenuContract.Column.title)
        val title: String,
        @ColumnInfo(name = MenuContract.Column.description)
        val description: String,
)