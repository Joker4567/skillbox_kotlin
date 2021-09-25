package com.prognozrnm.utils.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.prognozrnm.utils.platform.Event
import com.prognozrnm.utils.platform.EventObserver
import com.prognozrnm.utils.platform.Failure
import com.prognozrnm.utils.platform.State

fun <T : Any, L : LiveData<T>> LifecycleOwner.observeLifeCycle(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <T : Any?, L : LiveData<T>> Fragment.observeFragment(liveData: L, body: (T?) -> Unit) =
    liveData.observe(viewLifecycleOwner, Observer(body))

fun <T : Any?, L : LiveData<Event<T>>> Fragment.observeEvent(liveData: L, body: (T) -> Unit) =
    liveData.observe(viewLifecycleOwner, EventObserver(body))

fun <L : LiveData<Failure>> Fragment.failure(liveData: L, body: (Failure?) -> Unit) =
    liveData.observe(viewLifecycleOwner, Observer(body))

fun <T : Any?, L : LiveData<T>> AppCompatActivity.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

//fun <T : Any?, L : LiveData<Event<T>>> AppCompatActivity.observeEvent(
//    liveData: MutableLiveData<State>,
//    body: (T) -> Unit
//) =
//    liveData.observe(this, EventObserver(body))

fun <T : Any?, L : LiveData<Event<T>>> AppCompatActivity.observeEvent(
    liveData: L,
    body: (T) -> Unit
) =
    liveData.observe(this, EventObserver(body))