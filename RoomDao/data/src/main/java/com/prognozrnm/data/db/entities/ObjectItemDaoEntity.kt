package com.prognozrnm.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.prognozrnm.data.db.contract.ObjectItemContract
import com.prognozrnm.data.db.contract.UserAssignedContract
import com.prognozrnm.data.entity.ObjectItem
import com.prognozrnm.utils.platform.DiffItem
import java.io.Serializable

@Entity(tableName = ObjectItemContract.tableName)
data class ObjectItemDaoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ObjectItemContract.Column.id)
    val id: Int,
    @ColumnInfo(name = ObjectItemContract.Column.type)
    val type: String,
    @ColumnInfo(name = ObjectItemContract.Column.name)
    val name: String,
    @ColumnInfo(name = ObjectItemContract.Column.location)
    val location: List<Double>
) {
    fun from(): ObjectItem = ObjectItem(
        type, name, location
    )
}