package com.skillbox.networking.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovie(
    @Json(name = "imdbID")
    val id: String,
    @Json(name = "Title")
    val title: String,
    @Json(name = "Year")
    val year: Int,
    @Json(name = "Poster")
    val url:String,
    @Json(name = "Type")
    val type:TypeMovie = TypeMovie.all,
    @Json(name = "Ratings")
    val listRating:MutableList<Score> = emptyList<Score>().toMutableList(),
    @Json(name = "Rated")
    val rating: MovieRating = MovieRating.GENERAL
)