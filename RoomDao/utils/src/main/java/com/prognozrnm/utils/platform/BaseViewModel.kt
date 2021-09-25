package com.prognozrnm.utils.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()
    protected fun Disposable.addToDisposables() = disposables.add(this)
    val mainState = MutableLiveData<Event<State>>()

    protected fun handleState(state: State) {
        if (state is State.Error) {
            mainState.value = Event(state)
        } else
            mainState.value = Event(state)
    }

    protected fun handleStateWithExit(state: State) {
        mainState.value = Event(state)
    }

    protected fun launch(func: suspend () -> Unit) =
        viewModelScope.launch(Dispatchers.Main) { func.invoke() }

    protected fun launchIO(func: suspend () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) { func.invoke() }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
