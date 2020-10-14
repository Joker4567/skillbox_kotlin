package com.skillbox.lists12.Models

sealed class Pet {

    data class Dog(
        val id:Int,
        val name: String,
        val avatarLink: String,
        val poroda:Boolean

    ): Pet(){
        companion object {
            public const val TYPE_MODEL = 1
        }
    }

    data class Cat(
        val id:Int,
        val name: String,
        val avatarLink: String,
        val weight: Int
    ): Pet(){
        companion object {
            public const val TYPE_MODEL = 2
        }
    }

}