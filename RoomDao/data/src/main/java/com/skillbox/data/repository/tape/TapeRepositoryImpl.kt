package com.skillbox.data.repository.tape

import com.skillbox.data.db.dao.TapeDap
import com.skillbox.data.db.entities.Tape
import com.skillbox.data.db.entities.TapeWithDishAndRestaurant

class TapeRepositoryImpl(
        private val tapeDap: TapeDap
) : TapeRepository {
    override suspend fun addTape(item: Tape) {
        tapeDap.addTape(item)
    }

    override suspend fun getTape(): List<TapeWithDishAndRestaurant> = tapeDap.getTape()
    override suspend fun clear() {
        tapeDap.clear()
    }
}