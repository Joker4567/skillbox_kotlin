package com.skillbox.data.db.contract

object DishContract {
    const val tableName = "dish"
    object Column {
        const val title = "DishTitle"
        const val dishId = "dishId"
        const val price = "DishPrice"
        const val weight = "DishWeight"
        const val menuId = "DishMenuId"
    }
}