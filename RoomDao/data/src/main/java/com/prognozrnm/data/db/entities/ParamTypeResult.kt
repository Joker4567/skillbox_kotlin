package com.prognozrnm.data.db.entities

enum class ParamTypeResult {
    Wait, Send, Error;

    companion object {
        fun valueOf(i: Int): ParamTypeResult {
            return when (i) {
                1 -> Wait
                2 -> Send
                3 -> Error
                else -> Wait
            }
        }
    }
}