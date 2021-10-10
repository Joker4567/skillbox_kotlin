package com.skillbox.presentation.main

import androidx.room.withTransaction
import com.skillbox.data.db.SkillboxDB
import com.skillbox.data.db.entities.*
import com.skillbox.data.repository.menu.MenuRepository
import com.skillbox.data.repository.order.OrderRepository
import com.skillbox.data.repository.rating.RatingRepository
import com.skillbox.data.repository.restaurant.RestaurantRepository
import com.skillbox.data.repository.tape.TapeRepository
import com.skillbox.utils.platform.BaseViewModel

class MainViewModel(
        private val restaurantRepository: RestaurantRepository,
        private val menuRepository: MenuRepository,
        private val tapeRepository: TapeRepository,
        private val orderRepository: OrderRepository,
        private val ratingRepository: RatingRepository
) : BaseViewModel() {

    fun initDB() {
        launchIO {
            SkillboxDB.instance.withTransaction {
                restaurantRepository.clear()
                menuRepository.clear()
                tapeRepository.clear()
                orderRepository.clear()
                menuRepository.clearDish()

                restaurantRepository.addRestaurant(Restaurant(1, "Ресторан 1"))
                restaurantRepository.addRestaurant(Restaurant(2, "Ресторан 2"))
                restaurantRepository.addRestaurant(Restaurant(3, "Ресторан 3"))
                menuRepository.addMenu(Menu(1, 1, "Питательный набор", "Здоровые калории для мышц"))
                menuRepository.addMenu(Menu(2, 2, "Стройная талия", "Для похудения"))
                menuRepository.addMenu(Menu(3, 2, "Стройная талия 2", "Для похудения 2"))
                tapeRepository.addTape(Tape(1, "Здоровая неделя", "Скидка на мясо 10%", 3, 3))
                tapeRepository.addTape(Tape(2, "Спортивное питание", "2 каши по цене 1", 2, 3))
                tapeRepository.addTape(Tape(3, "Социальный набор", "Оптимальный набор для пенсионеров", 1, 3))
                orderRepository.addOrder(Order(1, 2, "13.09.2021", "12.09.2021", 550F, 0))
                orderRepository.addOrder(Order(2, 2, "12.09.2021", "11.09.2021", 1250F, 10))
                ratingRepository.addRating(Rating(1, "Еда", "Вкусные салаты и диетические блюда", 1, 2, 5))
                ratingRepository.addRating(Rating(2, "Обслуживание",
                        "еда на высоте, но ждать подачу еды долго и не расторопность официантов желает лучшего",
                        2, 3, 3))
                menuRepository.addDish(Dish(1, "Каша", 40F, 0.2F))
                menuRepository.addDish(Dish(2, "Котлета", 80F, 0.35F))
                menuRepository.addDishToOrder(DishToOrder(1, 1))
                menuRepository.addDishToOrder(DishToOrder(1, 2))
            }
        }
    }

}