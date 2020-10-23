package com.skillbox.networking.movie_list

data class RemoteMovie(
    val id: String,
    val title: String,
    val year: String,
    val url:String,
    val type:String
)