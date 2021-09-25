package com.prognozrnm.data.repository

import com.prognozrnm.data.db.entities.*
import com.prognozrnm.data.db.entities.ResultList
import com.prognozrnm.utils.platform.State

interface ResultRepository {
    //Вставка геометки
    suspend fun setObjGet(result: List<ObjDaoEntity>)
    //вставка заголовка объекта геометки
    suspend fun setObjGeoItem(result: List<ObjItemDaoEntity>)
    //Вставка занесённого результата по конкретному объекту
    suspend fun setResult(result: List<ResultObjDaoEntity>)
    //Вставить заголовок результата у объекта
    suspend fun setResultItem(result: List<ResultItemDaoEntity>)

    //получение последних записей по геометка, результатам объектов
    suspend fun getLastIdObjGeo(): Int
    suspend fun getLastIdObjGeoItem(): Int
    suspend fun getLastIdResult(): Int
    suspend fun getLastIdResultItem(): Int

    //Обновить результат у конкретного чек-листа в объекте
    suspend fun updateResult(item: ResultObjDaoEntity)
    //Обновить конкретную геометку
    suspend fun updateObj(item: ObjDaoEntity)
    //Удалить элемент геометки
    suspend fun removeItemMap(itemMap: ObjDaoEntity)
    //Получить результат объекта по idResult у объекта и id текущего CheckList
    suspend fun getResultItemWithResultObj(idResult: Int, idCheckList: Int): ResultObjDaoEntity?
    //Получить геометку объекта по idResult у объекта и id текущего элемента геометки
    suspend fun getObjItemWithObjDaoDaoEntity(idResult: Int, idMapObj: Int): ObjDaoEntity?
    //Получить геометку по idResult у объекта
    suspend fun getObjItemWithObjDaoDaoEntityList(idResult: Int): List<ObjDaoEntity>
    //Получить данные по результатам объектов все (склеинные данные по двум таблицам)
    suspend fun getResultItemWithResultObjAll(): List<ResultItemWithResultObjDaoEntity>
    //Получить данные по геометкам все (склеинные данные по двум таблицам)
    suspend fun getObjItemWithObjAll(): List<ObjItemWithObjDaoDaoEntity>
    //Получить список заголовков у геометок
    suspend fun getObjItemAll(): List<ObjItemDaoEntity>
    //Отправка фото результатов (конкретный объект и у него чек-лист)
    suspend fun postResult(
        obj: ResultList,
        onSuccess: (String) -> Unit,
        onState: (State) -> Unit
    )
    //Отправка фото геометки (конкретный объект и у него чек-лист)
    suspend fun postPhotoGeo(
        obj: ObjDaoEntity,
        data: ByteArray,
        onSuccess: (String) -> Unit,
        onState: (State) -> Unit
    )
    //Получить весь список результатов (конкретные объекты)
    suspend fun getResultAll(): List<ResultItemDaoEntity>
    //Получить весь список результатов (заголовок группы объектов(результата))
    suspend fun getResultItemAll(): List<ResultObjDaoEntity>
    //Обновить заголовок результатов (заголовок объектное слово)
    suspend fun updateResultItem(item: ResultItemDaoEntity)
    //Обновить заголовок геометок (заголовок объектное слово)
    suspend fun updateObjItem(item: ObjItemDaoEntity)
}