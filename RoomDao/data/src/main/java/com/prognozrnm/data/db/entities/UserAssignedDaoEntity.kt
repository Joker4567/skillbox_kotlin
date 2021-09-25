package com.prognozrnm.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.prognozrnm.data.db.contract.UserAssignedContract
import com.prognozrnm.data.entity.UserAssigned

@Entity(tableName = UserAssignedContract.tableName,
    indices = [Index(
        value = arrayOf(UserAssignedContract.Column.userId, UserAssignedContract.Column.objects, UserAssignedContract.Column.items),
        unique = true
    )])
data class UserAssignedDaoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UserAssignedContract.Column.id)
    val id: Int,
    @ColumnInfo(name = UserAssignedContract.Column.userId)
    val userId:String,
    @ColumnInfo(name = UserAssignedContract.Column.objects)
    val objects: Map<String, ObjectItemDaoEntity>,
    @ColumnInfo(name = UserAssignedContract.Column.items)
    val items: List<ItemsDaoEntity>
) {
    fun from():UserAssigned = UserAssigned(
        objects = objects.map { it.key to it.value.from() }.toMap(),
        items = items.map { it.mapItems() }
    )
}