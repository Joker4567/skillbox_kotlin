package com.prognozrnm.presentation.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import com.prognozrnm.data.db.ProspectorDatabase
import com.prognozrnm.data.db.entities.UserAssignedDaoEntity
import com.prognozrnm.data.db.entities.UserDaoEntity
import com.prognozrnm.data.entity.UserAssigned
import com.prognozrnm.data.entity.WorkList.*
import com.prognozrnm.data.repository.LoginRepository
import com.prognozrnm.data.repository.UserAssignedRepository
import com.prognozrnm.utils.platform.BaseViewModel

class HomeViewModel(
    private val userAssignedRepository: UserAssignedRepository,
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    var works = MutableLiveData<List<Work>>()

    init {
        downloadUserAssigned()
        setListWorkLocalDB(null)
    }

    //Загрузжаем работы
    fun downloadUserAssigned(refresh: Boolean = false) {
        if (works.value == null || refresh) {
            launchIO {
                val userId = loginRepository.getUserAuthById() ?: ""
                userAssignedRepository.getUserAssigned(
                    userId,
                    ::successUserAssigned,
                    onState = ::handleState
                )
            }
        }
    }

    //Сохраняем работы в локальную БД
    private fun successUserAssigned(list: UserAssigned) {
        launchIO {
            val userId = loginRepository.getUserAuthById() ?: ""
            val listDaoEntity = list.from(userId)
            userAssignedRepository.setUserAssigned(
                listDaoEntity,
                {
                    setListWorkLocalDB(list)
                    Log.d("Home", "Список работ успешно сохранён")
                },
                ::handleState
            )
        }
    }

    private fun setListWorkLocalDB(listSource: UserAssigned?) {
        launchIO {
            var workListLocal: UserAssigned?
            workListLocal = listSource
                ?: ProspectorDatabase.instance.withTransaction {
                    val userId = loginRepository.getUserAuthById() ?: ""
                    val listDao: UserAssignedDaoEntity? =
                        userAssignedRepository.getUserAssignedLocal(userId)
                    listDao?.from()
                }
            launch {
                val source = mutableListOf<Work>()
                workListLocal?.items?.forEach { items ->
                    source.add(
                        Work(
                            workListLocal.objects[items.objId]?.name.toString(),
                            items.name,
                            workListLocal.objects[items.objId]?.type!!.toByte(),
                            items.params,
                            items.comment,
                            items.resultId,
                            items.objId,
                            items.checklistType
                        )
                    )
                }
                works.postValue(source)
            }
        }
    }
}