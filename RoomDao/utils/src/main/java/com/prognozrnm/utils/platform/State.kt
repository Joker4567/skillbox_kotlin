package com.prognozrnm.utils.platform

sealed class State {
    object Loading : State()
    object Loaded : State()
    data class Error(val throwable: Failure) : State()
}