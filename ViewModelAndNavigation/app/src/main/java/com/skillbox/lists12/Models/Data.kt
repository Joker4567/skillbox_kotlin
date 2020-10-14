package com.skillbox.lists12.Models

class Data {

    companion object {
        private val instance: Data = Data()

        fun getInstance(): Data? {
            return instance
        }
    }
}