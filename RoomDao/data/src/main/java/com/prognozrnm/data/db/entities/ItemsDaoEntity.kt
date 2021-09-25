package com.prognozrnm.data.db.entities

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.prognozrnm.data.db.contract.ItemsContract
import com.prognozrnm.data.db.contract.UserAssignedContract
import com.prognozrnm.data.entity.Items
import com.prognozrnm.utils.platform.DiffItem
import java.io.Serializable

@Entity(tableName = ItemsContract.tableName,
    indices = [Index(
        value = arrayOf(ItemsContract.Column.name, ItemsContract.Column.objId),
        unique = true
    )])
data class ItemsDaoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ItemsContract.Column.id)
    val id: Int,
    @ColumnInfo(name = ItemsContract.Column.checklistType)
    val checklistType: Int,
    @ColumnInfo(name = ItemsContract.Column.name)
    val name: String,
    @ColumnInfo(name = ItemsContract.Column.objId)
    val objId: String,
    @ColumnInfo(name = ItemsContract.Column.resultId)
    val resultId: Int,
    @ColumnInfo(name = ItemsContract.Column.lastChange)
    val lastChange: Double,
    @ColumnInfo(name = ItemsContract.Column.params)
    val params: List<Int>,
    @ColumnInfo(name = ItemsContract.Column.comment)
    val comment: String
)
//Аналогичный map, замена from
fun ItemsDaoEntity.mapItems(): Items {
    return Items(
        checklistType, name, objId, resultId, lastChange, params, comment
    )
}