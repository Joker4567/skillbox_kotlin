package com.prognozrnm.data.db.entities

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.prognozrnm.data.db.contract.UserContract
import com.prognozrnm.utils.platform.DiffItem
import java.io.Serializable

@Entity(tableName = UserContract.tableName, indices = [Index(
    value = arrayOf(UserContract.Column.email),
    unique = true
)])
data class UserDaoEntity(
    @PrimaryKey
    @ColumnInfo(name = UserContract.Column.userId)
    val userId:String,
    @ColumnInfo(name = UserContract.Column.token)
    val token: String,
    @ColumnInfo(name = UserContract.Column.email)
    val email:String,
    @ColumnInfo(name = UserContract.Column.auth)
    val auth:Boolean
)