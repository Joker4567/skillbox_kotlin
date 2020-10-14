package com.skillbox.fragments11.service

interface ItemSelectListener {
    fun onClick()
    fun setList(state: MutableMap<String, Boolean>)
}