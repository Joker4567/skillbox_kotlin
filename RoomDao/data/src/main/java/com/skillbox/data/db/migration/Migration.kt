package com.skillbox.data.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.skillbox.data.db.contract.ComponentContract

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE ${ComponentContract.tableName} ADD COLUMN typeComponent TEXT NOT NULL")
    }
}