package com.skillbox.presentation.lenta

import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import com.skillbox.data.db.SkillboxDB
import com.skillbox.data.db.entities.*
import com.skillbox.data.repository.menu.MenuRepository
import com.skillbox.data.repository.order.OrderRepository
import com.skillbox.data.repository.rating.RatingRepository
import com.skillbox.data.repository.restaurant.RestaurantRepository
import com.skillbox.data.repository.tape.TapeRepository
import com.skillbox.presentation.lenta.model.LentaAdapterModel
import com.skillbox.utils.platform.BaseViewModel

class LentaViewModel(
        private val tapeRepository: TapeRepository
) : BaseViewModel() {

    var source = MutableLiveData<List<LentaAdapterModel>>()

    fun executeDao() {
        launchIO {
            val res = tapeRepository.getTape()
            launch {
                if (res.isNotEmpty()) {
                    val listTapeModel = emptyList<LentaAdapterModel>().toMutableList()
                    res.forEach { restaurant ->
                        restaurant.tapes.forEach { tape ->
                            val item = LentaAdapterModel(
                                    restaurant = restaurant.restaurant.name,
                                    title = tape.title,
                                    description = tape.description,
                                    restaurantId = tape.restaurantId
                            )
                            listTapeModel.add(item)
                        }
                    }
                    source.postValue(listTapeModel)
                }
            }
        }
    }
}