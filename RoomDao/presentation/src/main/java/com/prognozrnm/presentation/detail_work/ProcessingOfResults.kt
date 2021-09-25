package com.prognozrnm.presentation.detail_work

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.prognozrnm.data.db.ProspectorDatabase
import com.prognozrnm.data.db.entities.ParamTypeResult
import com.prognozrnm.data.entity.ParamResult
import com.prognozrnm.data.db.entities.ResultItemDaoEntity
import com.prognozrnm.data.db.entities.ResultObjDaoEntity
import com.prognozrnm.data.entity.WorkList.Work
import com.prognozrnm.data.repository.ResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProcessingOfResults(
    private val repositoryResult: ResultRepository,
    private val work: Work
) : ViewModel() {

    fun number(value: ParamResult, latitude: Double, longitude: Double) {
        launchIO {
            var result =
                repositoryResult.getResultItemWithResultObj(work.resultId, value.idCheckList)
            if (result != null) {
                repositoryResult.updateResult(
                    ResultObjDaoEntity(
                        result.id,
                        result.idCheckList,
                        value.result,
                        longitude,
                        latitude,
                        System.currentTimeMillis() / 1000L
                    )
                )
            } else {
                ProspectorDatabase.instance.withTransaction {
                    repositoryResult.setResult(
                        listOf(
                            ResultObjDaoEntity(
                                0,
                                value.idCheckList,
                                value.result,
                                longitude,
                                latitude,
                                System.currentTimeMillis() / 1000L
                            )
                        )
                    )
                    val id = repositoryResult.getLastIdResult()
                    repositoryResult.setResultItem(
                        listOf(
                            ResultItemDaoEntity(
                                0, work.resultId, id, ParamTypeResult.Wait,true
                            )
                        )
                    )
                }
            }
        }
    }

    fun text(value: ParamResult, latitude: Double, longitude: Double) {
        launchIO {
            var result =
                repositoryResult.getResultItemWithResultObj(work.resultId, value.idCheckList)
            if (result != null) {
                repositoryResult.updateResult(
                    ResultObjDaoEntity(
                        result.id,
                        result.idCheckList,
                        value.result,
                        longitude,
                        latitude,
                        System.currentTimeMillis() / 1000L
                    )
                )
            } else {
                ProspectorDatabase.instance.withTransaction {
                    repositoryResult.setResult(
                        listOf(
                            ResultObjDaoEntity(
                                0,
                                value.idCheckList,
                                value.result,
                                longitude,
                                latitude,
                                System.currentTimeMillis() / 1000L
                            )
                        )
                    )
                    val id = repositoryResult.getLastIdResult()
                    repositoryResult.setResultItem(
                        listOf(
                            ResultItemDaoEntity(
                                0, work.resultId, id, ParamTypeResult.Wait,true
                            )
                        )
                    )
                }
            }
        }
    }

    fun date(value: ParamResult, latitude: Double, longitude: Double) {
        launchIO {
            var result =
                repositoryResult.getResultItemWithResultObj(work.resultId, value.idCheckList)
            if (result != null) {
                repositoryResult.updateResult(
                    ResultObjDaoEntity(
                        result.id,
                        result.idCheckList,
                        value.result,
                        longitude,
                        latitude,
                        System.currentTimeMillis() / 1000L
                    )
                )
            } else {
                ProspectorDatabase.instance.withTransaction {
                    repositoryResult.setResult(
                        listOf(
                            ResultObjDaoEntity(
                                0,
                                value.idCheckList,
                                value.result,
                                longitude,
                                latitude,
                                System.currentTimeMillis() / 1000L
                            )
                        )
                    )
                    val id = repositoryResult.getLastIdResult()
                    repositoryResult.setResultItem(
                        listOf(
                            ResultItemDaoEntity(
                                0, work.resultId, id, ParamTypeResult.Wait,true
                            )
                        )
                    )
                }
            }
        }
    }

    fun time(value: ParamResult, latitude: Double, longitude: Double) {
        launchIO {
            var result =
                repositoryResult.getResultItemWithResultObj(work.resultId, value.idCheckList)
            if (result != null) {
                repositoryResult.updateResult(
                    ResultObjDaoEntity(
                        result.id,
                        result.idCheckList,
                        value.result,
                        longitude,
                        latitude,
                        System.currentTimeMillis() / 1000L
                    )
                )
            } else {
                ProspectorDatabase.instance.withTransaction {
                    repositoryResult.setResult(
                        listOf(
                            ResultObjDaoEntity(
                                0,
                                value.idCheckList,
                                value.result,
                                longitude,
                                latitude,
                                System.currentTimeMillis() / 1000L
                            )
                        )
                    )
                    val id = repositoryResult.getLastIdResult()
                    repositoryResult.setResultItem(
                        listOf(
                            ResultItemDaoEntity(
                                0, work.resultId, id, ParamTypeResult.Wait,true
                            )
                        )
                    )
                }
            }
        }
    }

    fun seconds(value: ParamResult, latitude: Double, longitude: Double) {
        launchIO {
            var result =
                repositoryResult.getResultItemWithResultObj(work.resultId, value.idCheckList)
            if (result != null) {
                repositoryResult.updateResult(
                    ResultObjDaoEntity(
                        result.id,
                        result.idCheckList,
                        value.result,
                        longitude,
                        latitude,
                        System.currentTimeMillis() / 1000L
                    )
                )
            } else {
                ProspectorDatabase.instance.withTransaction {
                    repositoryResult.setResult(
                        listOf(
                            ResultObjDaoEntity(
                                0,
                                value.idCheckList,
                                value.result,
                                longitude,
                                latitude,
                                System.currentTimeMillis() / 1000L
                            )
                        )
                    )
                    val id = repositoryResult.getLastIdResult()
                    repositoryResult.setResultItem(
                        listOf(
                            ResultItemDaoEntity(
                                0, work.resultId, id, ParamTypeResult.Wait,true
                            )
                        )
                    )
                }
            }
        }
    }

    fun bool(value: ParamResult, latitude: Double, longitude: Double) {
        launchIO {
            var result =
                repositoryResult.getResultItemWithResultObj(work.resultId, value.idCheckList)
            if (result != null) {
                repositoryResult.updateResult(
                    ResultObjDaoEntity(
                        result.id,
                        result.idCheckList,
                        value.result,
                        longitude,
                        latitude,
                        System.currentTimeMillis() / 1000L
                    )
                )
            } else {
                ProspectorDatabase.instance.withTransaction {
                    repositoryResult.setResult(
                        listOf(
                            ResultObjDaoEntity(
                                0,
                                value.idCheckList,
                                value.result,
                                longitude,
                                latitude,
                                System.currentTimeMillis() / 1000L
                            )
                        )
                    )
                    val id = repositoryResult.getLastIdResult()
                    repositoryResult.setResultItem(
                        listOf(
                            ResultItemDaoEntity(
                                0, work.resultId, id, ParamTypeResult.Wait,true
                            )
                        )
                    )
                }
            }
        }
    }

    fun launch(func: suspend () -> Unit) =
        viewModelScope.launch(Dispatchers.Main) { func.invoke() }

    fun launchIO(func: suspend () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) { func.invoke() }
}