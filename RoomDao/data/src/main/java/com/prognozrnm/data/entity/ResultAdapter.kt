package com.prognozrnm.data.entity

data class ResultAdapter(
    val zakaz:String,
    val objName:String,
    //id результат объекта
    val idResult:Int,
    //кол-во записей на сервере (чек-листов) Params
    val countWorkItemsServer:Int,
    //кол-во записей занесенных пользователем Params
    var countWorkItemLocal:Int,
    //Кол-во занесенныз реперов или пикетов
    var countObjMap:Int,
    //ип объекта площадка или трасса
    val typeObject:Int,
    //статус отправки 0 - ожидание, 1 - успех, 2 - ошибка
    val status:Byte = 0
)
