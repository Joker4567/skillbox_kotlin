package com.skillbox.presentation.menu

import androidx.lifecycle.MutableLiveData
import com.skillbox.data.db.entities.Menu
import com.skillbox.data.repository.menu.MenuRepository
import com.skillbox.utils.platform.BaseViewModel

class MenuViewModel(
        private val menuRepository: MenuRepository
) : BaseViewModel() {

    val source = MutableLiveData<List<Menu>>()

    fun getMenu(idRestaurant: Int) {
        launchIO {
            val resultMenuList = menuRepository.getMenuRestaurantId(idRestaurant)
            launch {
                if(resultMenuList.isNotEmpty()){
                    source.postValue(resultMenuList.first().menu)
                }
            }
        }
    }

}