package com.prognozrnm.presentation.send

import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import com.prognozrnm.data.db.ProspectorDatabase
import com.prognozrnm.data.db.entities.*
import com.prognozrnm.data.db.entities.ObjectItem
import com.prognozrnm.data.entity.*
import com.prognozrnm.data.repository.LoginRepository
import com.prognozrnm.data.repository.ResultRepository
import com.prognozrnm.data.repository.UserAssignedRepository
import com.prognozrnm.utils.platform.BaseViewModel
import com.prognozrnm.utils.platform.State

class SendViewModel(
    private val repositoryResult: ResultRepository,
    private val userAssignedRepository: UserAssignedRepository,
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    //Объект для передачи на сервер
    private lateinit var resultList: ResultList
    var listObjAdapter = emptyList<ResultAdapter>().toMutableList()
    val source = MutableLiveData<List<ResultAdapter>>()
    var status: Byte = 0

    init {
        load()
    }

    fun load() {
        listObjAdapter.clear()
        var result = emptyList<ResultItem>().toMutableList()
        var objectList = emptyList<ObjectItem>().toMutableList()
        launchIO {
            var resultObj = emptyList<ResultItemWithResultObjDaoEntity>()
            var resultMap = emptyList<ObjItemWithObjDaoDaoEntity>()
            ProspectorDatabase.instance.withTransaction {
                resultObj = repositoryResult.getResultItemWithResultObjAll()
                resultMap = repositoryResult.getObjItemWithObjAll()
            }
            launch {
                resultObj.groupBy { it.resItem.resultId }.forEach { map ->
                    var resultsTemp = emptyList<ObjItem>().toMutableList()
                    map.value.forEach {
                        val item = it.resObjList.first()
                        //Если были изменения в объекте, то добавляем данные на отправку
                        if (it.resItem.edit && it.resItem.send == ParamTypeResult.Wait || it.resItem.send == ParamTypeResult.Error) {
                            resultsTemp.add(
                                ObjItem(
                                    item.idCheckList,
                                    item.result,
                                    item.latitude,
                                    item.longitude,
                                    item.date
                                )
                            )
                            status = 0
                        }
                    }
                    //Если есть результаты для отправки, то добавляем лист
                    //иначе нет смысла создавать пустой объект для отправки
                    if (resultsTemp.any())
                        result.add(ResultItem(map.key, resultsTemp))
                }
                resultMap.groupBy { it.resItem.resultId }.forEach { map ->
                    var resultsTemp = emptyList<Obj>().toMutableList()
                    map.value.forEach {
                        val item = it.resObjList.first()
                        //Если были изменения в объекте, то добавляем данные на отправку
                        if (it.resItem.edit && it.resItem.send == ParamTypeResult.Wait || it.resItem.send == ParamTypeResult.Error) {
                            resultsTemp.add(
                                Obj(item.latitude, item.longitude, item.date)
                            )
                            status = 0
                        }
                    }
                    //Если есть результаты для отправки, то добавляем лист
                    //иначе нет смысла создавать пустой объект для отправки
                    if (resultsTemp.any())
                        objectList.add(ObjectItem(map.key, resultsTemp))
                }
                resultList = ResultList(result, objectList)
            }
            val userId = loginRepository.getUserAuthById() ?: ""
            val listDao: UserAssignedDaoEntity? =
                userAssignedRepository.getUserAssignedLocal(userId)
            launch {
                listDao?.let { userAssigned ->
                    userAssigned.items.forEach { item_obj ->
                        var type = 0
                        var name = ""
                        userAssigned.objects.forEach {
                            if (it.key == item_obj.objId) {
                                type = it.value.type.toInt()
                                name = it.value.name
                            }
                        }
                        listObjAdapter.add(
                            ResultAdapter(
                                item_obj.name,
                                name,
                                item_obj.resultId,
                                item_obj.params.size,
                                0,
                                0,
                                type,
                                this.status
                            )
                        )
                    }
                }
                result.groupBy { it.Id }.forEach { map ->
                    listObjAdapter.forEach {
                        if (it.idResult == map.key) {
                            it.countWorkItemLocal = map.value[0].results.size
                        }
                    }
                }
                objectList.groupBy { it.Id }.forEach { map ->
                    listObjAdapter.forEach {
                        if (it.idResult == map.key)
                            it.countObjMap = map.value[0].results.size
                    }
                }
                source.postValue(listObjAdapter)
            }
        }
    }

    fun uploadResult(): String {
        return if (this.status != 1.toByte()) {
            launchIO {
                repositoryResult.postResult(resultList, {
                    updateSuccess()
                }, {
                    handleState(it)
                    if (it is State.Error)
                        updateError()
                })
            }
            ""
        } else {
            "Нет данных для отправки"
        }
    }

    private fun updateSuccess() {
        launchIO {
            val list = repositoryResult.getResultAll()
            launch {
                list.forEach {
                    launchIO {
                        ProspectorDatabase.instance.withTransaction {
                            repositoryResult.updateResultItem(
                                ResultItemDaoEntity(
                                    it.id,
                                    it.resultId,
                                    it.id_results,
                                    ParamTypeResult.Send,
                                    false
                                )
                            )
                        }
                    }
                }
            }
            val list2 = repositoryResult.getObjItemAll()
            launch {
                list2.forEach {
                    launchIO {
                        ProspectorDatabase.instance.withTransaction {
                            repositoryResult.updateObjItem(
                                ObjItemDaoEntity(
                                    it.id,
                                    it.resultId,
                                    it.id_obj,
                                    ParamTypeResult.Send,
                                    false
                                )
                            )
                        }
                    }
                }
            }
            load()
            status = 1
        }
    }

    private fun updateError() {
        launchIO {
            val list = repositoryResult.getResultAll()
            launch {
                list.forEach {
                    launchIO {
                        ProspectorDatabase.instance.withTransaction {
                            repositoryResult.updateResultItem(
                                ResultItemDaoEntity(
                                    it.id,
                                    it.resultId,
                                    it.id_results,
                                    ParamTypeResult.Error
                                )
                            )
                        }
                    }
                }
            }
            val list2 = repositoryResult.getObjItemAll()
            launch {
                list2.forEach {
                    launchIO {
                        ProspectorDatabase.instance.withTransaction {
                            repositoryResult.updateObjItem(
                                ObjItemDaoEntity(
                                    it.id,
                                    it.resultId,
                                    it.id_obj,
                                    ParamTypeResult.Error
                                )
                            )
                        }
                    }
                }
            }
            load()
            status = 2
        }
    }
}