package com.prognozrnm.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prognozrnm.data.db.contract.CheckListContract
import com.prognozrnm.data.db.entities.CheckListDaoEntity
import com.prognozrnm.data.db.entities.CheckListItemDaoEntity

@Dao
interface CheckListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setCheckList(checkList: List<CheckListDaoEntity>)

    @Query("SELECT * FROM ${CheckListContract.tableName}")
    fun getCheckList(): List<CheckListDaoEntity>
}