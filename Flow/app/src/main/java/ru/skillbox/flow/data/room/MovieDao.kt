package ru.skillbox.flow.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setMovie(result: List<MovieDaoEntity>)

    @Query("SELECT * FROM ${MovieContract.tableName} WHERE ${MovieContract.Column.title} Like '%' || :search || '%' AND ${MovieContract.Column.type} Like :typeMovie")
    fun observeMovies(search: String, typeMovie: String) : Flow<List<MovieDaoEntity>>
}