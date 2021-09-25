package com.prognozrnm.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.prognozrnm.data.db.contract.CheckListContract

@Entity(tableName = CheckListContract.tableName,
    indices = [Index(
        value = arrayOf(CheckListContract.Column.checkList),
        unique = true
    )])
data class CheckListDaoEntity(
    @PrimaryKey
    @ColumnInfo(name = CheckListContract.Column.id)
    val id: Int,
    @ColumnInfo(name = CheckListContract.Column.checkList)
    val checkList:List<CheckListItemDaoEntity>
)