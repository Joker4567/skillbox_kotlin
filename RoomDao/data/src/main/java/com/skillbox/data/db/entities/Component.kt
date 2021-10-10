package com.skillbox.data.db.entities

import androidx.room.*
import com.skillbox.data.db.contract.*

@Entity(tableName = ComponentContract.tableName,
        indices = [Index(
                value = arrayOf(ComponentContract.Column.title),
                unique = true
        )])
data class Component(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = ComponentContract.Column.id)
        val id: Int,
        @ColumnInfo(name = ComponentContract.Column.title)
        val title: String,
        val typeComponent: TypeComponent
)