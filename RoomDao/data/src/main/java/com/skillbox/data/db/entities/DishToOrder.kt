package com.skillbox.data.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.skillbox.data.db.contract.DishContract
import com.skillbox.data.db.contract.DishToOrderContract

@Entity(primaryKeys = ["orderId", "dishId"])
data class DishToOrder(
       val orderId: Int,
       val dishId: Int
)

data class OrderWithDish(
        @Embedded val order: Order,
        @Relation(
                parentColumn = DishToOrderContract.Column.orderId,
                entityColumn = DishToOrderContract.Column.dishId,
                associateBy = Junction(DishToOrder::class)
        ) val dish:List<Dish>
)

data class DishWithOrder(
        @Embedded val dish: Dish,
        @Relation(
                parentColumn = DishToOrderContract.Column.dishId,
                entityColumn = DishToOrderContract.Column.orderId,
                associateBy = Junction(DishToOrder::class)
        ) val order:List<Order>
)
