package com.skillbox.presentation.rating

import androidx.lifecycle.MutableLiveData
import com.skillbox.data.repository.rating.RatingRepository
import com.skillbox.data.repository.restaurant.RestaurantRepository
import com.skillbox.presentation.rating.model.RatingAdapterModel
import com.skillbox.utils.platform.BaseViewModel
import kotlin.random.Random

class RatingViewModel(
        private val ratingRepository: RatingRepository,
        private val restaurantRepository: RestaurantRepository
) : BaseViewModel() {

    val source = MutableLiveData<List<RatingAdapterModel>>()

    fun getRating() {
        launchIO {
            val rest = restaurantRepository.getRestaurantList().first()

            restaurantRepository.updateRestaurant(rest.apply {
                this.name = "Ресторан [изменная запись]"
            })

            val res = ratingRepository.getRatingAll()
            launch {
                if (res.isNotEmpty()) {
                    val itemListOrder = emptyList<RatingAdapterModel>().toMutableList()
                    res.forEach { restaurant ->
                        restaurant.ratings.forEach { ratingItem ->
                            val item = RatingAdapterModel(
                                    restaurant = restaurant.restaurant.name,
                                    stars = ratingItem.stars,
                                    description = ratingItem.description,
                                    title = ratingItem.title
                            )
                            itemListOrder.add(item)
                        }
                    }
                    source.postValue(itemListOrder)
                }
            }
        }
    }
}