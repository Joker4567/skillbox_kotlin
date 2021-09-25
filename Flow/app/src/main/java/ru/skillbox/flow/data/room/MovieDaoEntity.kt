package ru.skillbox.flow.data.room

import androidx.room.*

@Entity(tableName = MovieContract.tableName, indices = [Index(
        value = arrayOf(MovieContract.Column.title),
        unique = true
)])
data class MovieDaoEntity(
        @PrimaryKey
        @ColumnInfo(name = MovieContract.Column.id)
        val id: String,
        @ColumnInfo(name = MovieContract.Column.title)
        val title: String,
        @ColumnInfo(name = MovieContract.Column.year)
        val year: String,
        @ColumnInfo(name = MovieContract.Column.url)
        val url:String,
        @ColumnInfo(name = MovieContract.Column.type)
        val type:String
)
