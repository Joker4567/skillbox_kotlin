package com.skillbox.datetimepermission.Models

import java.time.Instant

class Data {

    private var locationList = emptyList<Message>()

    fun addLocation(message:Message):Int{
        locationList = locationList + listOf(message)
        return locationList.indexOf(message)
    }

    fun editLocationInDate(position:Int, state: org.threeten.bp.Instant){
        locationList[position]?.dateTime = state
    }

    fun getLocationList() : List<Message> {
        return locationList
    }

    companion object {
        private val instance: Data = Data()

        fun getInstance(): Data? {
            return instance
        }
    }
}