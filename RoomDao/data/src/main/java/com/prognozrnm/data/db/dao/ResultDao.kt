package com.prognozrnm.data.db.dao

import androidx.room.*
import com.prognozrnm.data.db.contract.*
import com.prognozrnm.data.db.entities.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Dao
interface ResultDao {
    //Вставка геометки
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setObjGet(result: List<ObjDaoEntity>)

    //Список геометок, вставка зависещей таблицы
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setObjGeoItem(result: List<ObjItemDaoEntity>)

    //Вставка результата конкретного чек-листа по объекту
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setResult(result: List<ResultObjDaoEntity>)

    //Список результатов, вставка зависещей таблицы
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setResultItem(result: List<ResultItemDaoEntity>)

    //Получить последнюю запись в таблице (id)
    @Query("SELECT ${ObjContract.Column.id} FROM ${ObjContract.tableName} ORDER BY ${ObjContract.Column.id} DESC LIMIT 1")
    fun getLastIdObjGeo(): Int

    @Query("SELECT ${ObjItemContract.Column.id} FROM ${ObjItemContract.tableName} ORDER BY ${ObjItemContract.Column.id} DESC LIMIT 1")
    fun getLastIdObjGeoItem(): Int

    @Query("SELECT ${ResultObjContract.Column.id} FROM ${ResultObjContract.tableName} ORDER BY ${ResultObjContract.Column.id} DESC LIMIT 1")
    fun getLastIdResult(): Int

    @Query("SELECT ${ResultItemContract.Column.id} FROM ${ResultItemContract.tableName} ORDER BY ${ResultItemContract.Column.id} DESC LIMIT 1")
    fun getLastIdResultItem(): Int

    @Query("SELECT * FROM ${ResultItemContract.tableName} WHERE ${ResultItemContract.Column.resultId} = :idResult")
    fun getResult(idResult: Int): List<ResultItemDaoEntity>
    @Query("SELECT * FROM ${ResultObjContract.tableName} WHERE ${ResultObjContract.Column.idCheckList} = :idCheckList")
    fun getResultItem(idCheckList: Int): List<ResultObjDaoEntity>

    @Query("SELECT * FROM ${ResultItemContract.tableName}")
    fun getResultAll(): List<ResultItemDaoEntity>
    @Query("SELECT * FROM ${ResultObjContract.tableName}")
    fun getResultItemAll(): List<ResultObjDaoEntity>

    @Query("SELECT * FROM ${ResultItemContract.tableName} WHERE ${ResultItemContract.Column.resultId} = :idResult")
    fun getResultItemWithResultObj(idResult:Int): List<ResultItemWithResultObjDaoEntity>

    @Query("SELECT * FROM ${ResultItemContract.tableName}")
    fun getResultItemWithResultObj():List<ResultItemWithResultObjDaoEntity>

    @Query("SELECT * FROM ${ObjItemContract.tableName} WHERE ${ObjItemContract.Column.resultId} = :idResult")
    fun getObjItemWithObj(idResult:Int) : List<ObjItemWithObjDaoDaoEntity>

    @Query("SELECT * FROM ${ObjItemContract.tableName}")
    fun getObjItemWithObjAll() : List<ObjItemWithObjDaoDaoEntity>

    @Query("SELECT * FROM ${ObjItemContract.tableName}")
    fun getObjItemAll() : List<ObjItemDaoEntity>

    @Update
    fun updateResultItem(item:ResultItemDaoEntity)
    @Update
    fun updateObjItem(item:ObjItemDaoEntity)

    @Update
    fun updateResult(item: ResultObjDaoEntity)
    @Update
    fun updateObj(item: ObjDaoEntity)

    @Delete
    fun removeItemMap(itemMap: ObjDaoEntity)
}