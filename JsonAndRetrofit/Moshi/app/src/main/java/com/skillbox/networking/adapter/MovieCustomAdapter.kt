package com.skillbox.networking.adapter

import com.skillbox.networking.model.MovieRating
import com.skillbox.networking.model.RemoteMovie
import com.skillbox.networking.model.Score
import com.skillbox.networking.model.TypeMovie
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class MovieCustomAdapter {

    @FromJson
    fun fromJson(customMovie: RemoteMovie): CustomMovie {
        var map: MutableMap<String, String> = emptyMap<String, String>().toMutableMap()
        customMovie.listRating.map { score ->
            map.put(score.source, score.value)
        }
        return CustomMovie(
            id = customMovie.id,
            title = customMovie.title,
            year = customMovie.year,
            rating = customMovie.rating,
            type = customMovie.type,
            url = customMovie.url,
            listRating = map.toMap() as HashMap<String, String>
        )
    }

    @JsonClass(generateAdapter = true)
    data class CustomMovie(
        @Json(name = "imdbID")
        val id: String,
        @Json(name = "Title")
        val title: String,
        @Json(name = "Year")
        val year: Int,
        @Json(name = "Poster")
        val url:String,
        @Json(name = "Type")
        val type: TypeMovie = TypeMovie.all,
        @Json(name = "Ratings")
        val listRating:HashMap<String, String>,
        @Json(name = "Rated")
        val rating: MovieRating = MovieRating.GENERAL
    )
}