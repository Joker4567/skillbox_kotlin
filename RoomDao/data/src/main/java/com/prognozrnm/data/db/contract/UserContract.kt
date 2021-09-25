package com.prognozrnm.data.db.contract

object UserContract {
    const val tableName = "users"
    object Column {
        const val userId = "userId"
        const val token = "token"
        const val email = "email"
        const val auth = "auth"
    }
}