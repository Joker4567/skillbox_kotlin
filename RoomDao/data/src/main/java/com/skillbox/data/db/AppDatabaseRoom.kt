package com.skillbox.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skillbox.data.db.SkillboxDB.Companion.DATABASE_VERSION
import com.skillbox.data.db.converter.TypeComponentConverter
import com.skillbox.data.db.dao.*
import com.skillbox.data.db.entities.*
import com.skillbox.data.db.migration.MIGRATION_1_2

@Database(
        entities = [
            Component::class,
            Dish::class,
            Menu::class,
            Rating::class,
            Restaurant::class,
            Tape::class,
            Order::class,
            DishToOrder::class
        ],
        version = DATABASE_VERSION
)
@TypeConverters(
        TypeComponentConverter::class
)
abstract class SkillboxDB : RoomDatabase() {

    abstract fun menuDao(): MenuDao
    abstract fun restaurantDao(): RestaurantDao
    abstract fun tapeDao(): TapeDap
    abstract fun orderDao(): OrderDao
    abstract fun ratingDao(): RatingDao

    companion object {
        lateinit var instance: SkillboxDB
            private set
        const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "SkillBoxDatabase"
        fun buildDataSource(context: Context): SkillboxDB {
            val room = Room.databaseBuilder(context, SkillboxDB::class.java, DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build()
            instance = room
            return room
        }
    }
}