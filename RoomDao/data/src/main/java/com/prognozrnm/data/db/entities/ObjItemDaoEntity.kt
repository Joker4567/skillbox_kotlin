package com.prognozrnm.data.db.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.prognozrnm.data.db.contract.ObjContract
import com.prognozrnm.data.db.contract.ObjItemContract
import com.prognozrnm.data.db.contract.ResultItemContract
import com.prognozrnm.data.db.contract.ResultObjContract

@Entity(tableName = ObjItemContract.tableName,
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            entity = ObjDaoEntity::class,
            childColumns = [ObjItemContract.Column.id_obj],
            parentColumns = [ObjContract.Column.id]
        )
    ],
    indices = [Index(value = arrayOf(ObjItemContract.Column.id_obj))])
//Список геометок у объектов
data class ObjItemDaoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ObjItemContract.Column.id)
    val id:Int,
    @ColumnInfo(name = ObjItemContract.Column.resultId)
    val resultId:Int,
    @ColumnInfo(name = ObjItemContract.Column.id_obj)
    val id_obj:Int,
    //Флаг был ли отправлен результат по данному объекту
    @ColumnInfo(name = ObjItemContract.Column.send)
    var send:ParamTypeResult = ParamTypeResult.Wait,
    //Флаг были ли изменения по данному объекту
    @ColumnInfo(name = ObjItemContract.Column.edit)
    var edit:Boolean = false
)
@Entity(tableName = ObjContract.tableName)
//Геометка объекта
data class ObjDaoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ObjContract.Column.id)
    val id:Int,
    @ColumnInfo(name = ObjContract.Column.longitude)
    var longitude: Double,
    @ColumnInfo(name = ObjContract.Column.latitude)
    var latitude: Double,
    @ColumnInfo(name = ObjContract.Column.date)
    val date:Long
)

data class ObjItemWithObjDaoDaoEntity(
    @Embedded
    val resItem:ObjItemDaoEntity,
    @Relation(
        parentColumn = ObjItemContract.Column.id_obj,
        entityColumn = ObjContract.Column.id
    )
    val resObjList:List<ObjDaoEntity>
)