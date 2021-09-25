package com.prognozrnm.data.db.dao

import androidx.room.*
import com.prognozrnm.data.db.contract.UserContract
import com.prognozrnm.data.db.entities.UserDaoEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUser(user: UserDaoEntity)

    @Query("SELECT * FROM ${UserContract.tableName} WHERE ${UserContract.Column.email} = :username")
    suspend fun getUserById(username: String): UserDaoEntity?

    @Query("SELECT * FROM ${UserContract.tableName} WHERE ${UserContract.Column.auth} = 1")
    suspend fun getUserAuth(): UserDaoEntity?

    @Query("SELECT ${UserContract.Column.userId} FROM ${UserContract.tableName} WHERE ${UserContract.Column.auth} = 1")
    suspend fun getUserAuthById(): String?

    @Update
    suspend fun updateUser(user: UserDaoEntity)

    @Query("DELETE FROM ${UserContract.tableName} WHERE ${UserContract.Column.userId} = :userId")
    suspend fun removeUserById(userId: Int)
}
