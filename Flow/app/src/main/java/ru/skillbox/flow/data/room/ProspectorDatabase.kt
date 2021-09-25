package ru.skillbox.flow.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.skillbox.flow.data.room.ProspectorDatabase.Companion.DATABASE_VERSION

@Database(
        entities = [MovieDaoEntity::class],
        version = DATABASE_VERSION,
        exportSchema = false
)
abstract class ProspectorDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        lateinit var instance: ProspectorDatabase
            private set
        const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SkillboxDatabase"

        fun buildDataSource(context: Context): ProspectorDatabase {
            instance = Room.databaseBuilder(context, ProspectorDatabase::class.java, DATABASE_NAME)
                    .build()
            return instance
        }
    }
}