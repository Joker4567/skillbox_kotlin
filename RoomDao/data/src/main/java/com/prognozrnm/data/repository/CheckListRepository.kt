package com.prognozrnm.data.repository

import androidx.lifecycle.LiveData
import com.prognozrnm.data.db.entities.CheckListDaoEntity
import com.prognozrnm.data.db.entities.CheckListItemDaoEntity
import com.prognozrnm.data.entity.CheckList
import com.prognozrnm.data.entity.CheckListItem
import com.prognozrnm.utils.platform.State

interface CheckListRepository {
    //скачать через API чек-листы
    suspend fun getCheckList(
        onSuccess: (List<CheckList>) -> Unit,
        onState: (State) -> Unit
    )
    //Сохранить чек-листы в локальную БД
    suspend fun setCheckList(
        list: List<CheckListDaoEntity>,
        onSuccess: (Unit) -> Unit,
        onState: (State) -> Unit
    )
    //Получить чек-листы из локальной БД
    suspend fun getCheckListLocal(): List<CheckListDaoEntity>
}