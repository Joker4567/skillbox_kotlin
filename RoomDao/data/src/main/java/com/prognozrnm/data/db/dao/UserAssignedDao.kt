package com.prognozrnm.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prognozrnm.data.db.contract.UserAssignedContract
import com.prognozrnm.data.db.entities.UserAssignedDaoEntity

@Dao
interface UserAssignedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUserAssigned(userAssignedList: UserAssignedDaoEntity)

    @Query("SELECT * FROM ${UserAssignedContract.tableName} WHERE ${UserAssignedContract.Column.userId} = :userId")
    fun getUserAssigned(userId:String): UserAssignedDaoEntity
}