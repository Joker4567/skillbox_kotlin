package com.prognozrnm.data.db.entities

//Дата класс для отправки результатов на сервер

data class ResultList(
    val result: List<ResultItem>,
    val objectList: List<ObjectItem>
)

data class ResultItem(
    val Id: Int,
    val results: List<ObjItem>
)

data class ObjItem(
    val id: Int,
    val result: String,
    val latitude: Double,
    val longitude: Double,
    val date: Long
)

data class ObjectItem(
    val Id: Int,
    val results: List<Obj>
)

data class Obj(
    val latitude: Double,
    val longitude: Double,
    val date: Long
)
