package com.skillbox.room.di.module

import android.content.Context
import com.skillbox.data.db.SkillboxDB
import org.koin.dsl.module

val roomModule = module {

    single { provideDatabase(get()) }
    single { checkMenuDao(get()) }
    single { checkRestaurantDao(get()) }
    single { checkTapeDao(get()) }
    single { checkOrderDao(get()) }
    single { checkRatingDao(get()) }
}

fun provideDatabase(context: Context) = SkillboxDB.buildDataSource(context)

fun checkMenuDao(skillboxDB: SkillboxDB) = skillboxDB.menuDao()
fun checkRestaurantDao(skillboxDB: SkillboxDB) = skillboxDB.restaurantDao()
fun checkTapeDao(skillboxDB: SkillboxDB) = skillboxDB.tapeDao()
fun checkOrderDao(skillboxDB: SkillboxDB) = skillboxDB.orderDao()
fun checkRatingDao(skillboxDB: SkillboxDB) = skillboxDB.ratingDao()
