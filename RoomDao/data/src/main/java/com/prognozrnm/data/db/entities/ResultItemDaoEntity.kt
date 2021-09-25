package com.prognozrnm.data.db.entities

import androidx.room.*
import com.prognozrnm.data.db.contract.ResultItemContract
import com.prognozrnm.data.db.contract.ResultObjContract

@Entity(tableName = ResultItemContract.tableName,
    foreignKeys = [
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            entity = ResultObjDaoEntity::class,
            parentColumns = [ResultObjContract.Column.id],
            childColumns = [ResultItemContract.Column.id_result]
        )
    ], indices = [Index(value = arrayOf(ResultItemContract.Column.id_result))]
)
//Список результатов у объектов
data class ResultItemDaoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ResultItemContract.Column.id)
    val id: Int,
    @ColumnInfo(name = ResultItemContract.Column.resultId)
    val resultId: Int,
    @ColumnInfo(name = ResultItemContract.Column.id_result)
    val id_results: Int,
    @ColumnInfo(name = ResultItemContract.Column.send)
    var send:ParamTypeResult = ParamTypeResult.Wait,
    //Флаг были ли изменения по данному объекту
    @ColumnInfo(name = ResultItemContract.Column.edit)
    var edit:Boolean = false
)

@Entity(tableName = ResultObjContract.tableName)
//Результат по конкретному чек-листу в объекте
data class ResultObjDaoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ResultObjContract.Column.id)
    val id: Int,
    @ColumnInfo(name = ResultObjContract.Column.idCheckList)
    val idCheckList: Int,
    @ColumnInfo(name = ResultObjContract.Column.result)
    val result: String,
    @ColumnInfo(name = ResultObjContract.Column.longitude)
    val longitude: Double,
    @ColumnInfo(name = ResultObjContract.Column.latitude)
    val latitude: Double,
    @ColumnInfo(name = ResultObjContract.Column.date)
    val date: Long
)

data class ResultItemWithResultObjDaoEntity(
    @Embedded
    val resItem:ResultItemDaoEntity,
    @Relation(
        parentColumn = ResultItemContract.Column.id_result,
        entityColumn = ResultObjContract.Column.id
    )
    val resObjList:List<ResultObjDaoEntity>
)