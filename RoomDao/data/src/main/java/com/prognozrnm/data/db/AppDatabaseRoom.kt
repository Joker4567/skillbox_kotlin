package com.prognozrnm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prognozrnm.data.db.ProspectorDatabase.Companion.DATABASE_VERSION
import com.prognozrnm.data.db.converter.*
import com.prognozrnm.data.db.dao.*
import com.prognozrnm.data.db.entities.*

@Database(
    entities = [
        CheckListDaoEntity::class,
        UserDaoEntity::class,
        UserAssignedDaoEntity::class,
        ObjectItemDaoEntity::class,
        ResultItemDaoEntity::class,
        ResultObjDaoEntity::class,
        ObjItemDaoEntity::class,
        ObjDaoEntity::class],
    version = DATABASE_VERSION
)
@TypeConverters(
    CheckListItemConverter::class,
    ListDoubleConverter::class,
    ListIntConverter::class,
    UserAssignedListConverter::class,
    UserAssignedMapConverter::class,
    ResultConverter::class
)
abstract class ProspectorDatabase : RoomDatabase() {

    abstract fun checkListDao(): CheckListDao
    abstract fun userDao(): UserDao
    abstract fun userAssignedDao(): UserAssignedDao
    abstract fun resultDao(): ResultDao

    companion object {
        lateinit var instance: ProspectorDatabase
            private set
        const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ProspectorDatabase"
        fun buildDataSource(context: Context): ProspectorDatabase {
            val room = Room.databaseBuilder(context, ProspectorDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
            instance = room
            return room
        }
    }
}