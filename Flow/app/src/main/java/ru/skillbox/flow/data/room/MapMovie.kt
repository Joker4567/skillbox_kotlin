package ru.skillbox.flow.data.room

import ru.skillbox.flow.model.Movie

fun Movie.mapToDB() =
        MovieDaoEntity(
                id,
                title,
                year,
                url,
                type
        )

fun MovieDaoEntity.mapToNetwork() =
        Movie(
                id,
                title,
                year,
                url,
                type
        )