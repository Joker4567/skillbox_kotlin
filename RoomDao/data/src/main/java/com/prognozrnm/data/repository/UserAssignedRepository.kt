package com.prognozrnm.data.repository

import com.prognozrnm.data.db.entities.UserAssignedDaoEntity
import com.prognozrnm.data.entity.UserAssigned
import com.prognozrnm.utils.platform.State
import kotlin.reflect.KFunction1

interface UserAssignedRepository {
    //Получить список работ с сервера
    suspend fun getUserAssigned(
        idUser: String,
        onSuccess: (UserAssigned) -> Unit,
        onState: (State) -> Unit
    )
    //Сохранить в локальную БД
    suspend fun setUserAssigned(
        list: UserAssignedDaoEntity,
        onSuccess: (Unit) -> Unit,
        onState: (State) -> Unit
    )
    //Получить список работ из локальной БД
    suspend fun getUserAssignedLocal(userId:String):UserAssignedDaoEntity
}