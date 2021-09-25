package com.prognozrnm.data.repository

import com.prognozrnm.data.db.entities.CheckListDaoEntity
import com.prognozrnm.data.db.entities.UserDaoEntity
import com.prognozrnm.data.entity.UserInfo
import com.prognozrnm.utils.platform.State

interface LoginRepository {
    //авторизация пользователя
    suspend fun loginAuth(
        login: String,
        password: String,
        onSuccess: (UserInfo) -> Unit,
        onState: (State) -> Unit
    )

    //Заносим пользователя в локальную базу данных
    suspend fun setUserLocal(
        user: UserDaoEntity,
        onSuccess: (Unit) -> Unit,
        onState: (State) -> Unit
    )

    //Поиск ранее авторизованного пользователя
    suspend fun getUserAuth(): UserDaoEntity?

    //Получить Id авторизованного пользователя
    suspend fun getUserAuthById() : String?

    //Обновить данные пользователя
    suspend fun updateUser(
        user: UserDaoEntity,
        onSuccess: (Unit) -> Unit,
        onState: (State) -> Unit
    )
    //Удалить пользователя из БД
    suspend fun removeUserById(
        userId: Int,
        onSuccess: (Unit) -> Unit,
        onState: (State) -> Unit
    )
}