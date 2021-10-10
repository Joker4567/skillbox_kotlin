package com.skillbox.utils.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.skillbox.utils.platform.Event
import com.skillbox.utils.platform.EventObserver

fun <T : Any, L : LiveData<T>> LifecycleOwner.observeLifeCycle(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <T : Any?, L : LiveData<T>> Fragment.observeFragment(liveData: L, body: (T?) -> Unit) =
    liveData.observe(viewLifecycleOwner, Observer(body))

fun <T : Any?, L : LiveData<Event<T>>> Fragment.observeEvent(liveData: L, body: (T) -> Unit) =
    liveData.observe(viewLifecycleOwner, EventObserver(body))

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