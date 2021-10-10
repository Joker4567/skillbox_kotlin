package com.skillbox.data.repository.tape

import com.skillbox.data.db.entities.Tape
import com.skillbox.data.db.entities.TapeWithDishAndRestaurant

interface TapeRepository {
    suspend fun addTape(item: Tape)
    suspend fun getTape() : List<TapeWithDishAndRestaurant>
    suspend fun clear()
}