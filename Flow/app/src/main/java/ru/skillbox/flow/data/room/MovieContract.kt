package ru.skillbox.flow.data.room

object MovieContract {
    const val tableName = "movie_table"
    object Column {
        const val id = "id"
        const val title = "title"
        const val year = "year"
        const val url = "url"
        const val type = "type"
    }
}