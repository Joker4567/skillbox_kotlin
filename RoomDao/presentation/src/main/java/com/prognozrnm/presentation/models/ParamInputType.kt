package com.prognozrnm.presentation.models

enum class ParamInputType {
    Null, Select, Number, Text, Boolean, Time, DateTime, Seconds;

    companion object {
        fun valueOf(i: Int): ParamInputType {
            return when (i) {
                1 -> Select
                2 -> Number
                3 -> Text
                4 -> Boolean
                5 -> Time
                6 -> DateTime
                7 -> Seconds
                else -> Text
            }
        }
    }
}