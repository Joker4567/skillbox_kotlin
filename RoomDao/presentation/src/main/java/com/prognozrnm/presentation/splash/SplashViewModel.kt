package com.prognozrnm.presentation.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.prognozrnm.data.entity.CheckList
import com.prognozrnm.data.entity.CheckListItem
import com.prognozrnm.data.repository.CheckListRepository
import com.prognozrnm.utils.platform.BaseViewModel
import com.prognozrnm.utils.platform.Event
import com.prognozrnm.utils.platform.State

class SplashViewModel(
    private val checkListRepository: CheckListRepository
) : BaseViewModel() {
    val eventLoader = MutableLiveData<Event<State>>()
    val eventButton = MutableLiveData<Boolean>()
    val eventStartApp = MutableLiveData<Boolean>()

    init {
        getCheckList()
    }

    fun getCheckList() {
        launch {
            checkListRepository.getCheckList(::handleData, ::handleError)
        }
    }

    //Сохраняем чек-листы
    private fun handleData(list: List<CheckList>) {
        //сохранить в базу данных SQLite
        launchIO {
            checkListRepository.setCheckList(
                list.map {
                    it.from()
                },
                onSuccess = {
                    eventButton.postValue(false)
                    eventLoader.value = Event(State.Loaded)
                },
                onState = ::handleState
            )
        }
    }

    private fun handleError(state: State) {
        launchIO {
            if (checkListRepository.getCheckListLocal().any())
                launch {
                    eventStartApp.postValue(true)
                    eventButton.postValue(false)
                }
            else {
                launch {
                    handleState(state)
                    eventButton.postValue(true)
                }
            }
        }
    }
}