package com.example.movierecommendations.model

import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    // ✅ 根據分類查詢
    @Query("SELECT * FROM movies WHERE category = :category")
    suspend fun getMoviesByCategory(category: String): List<MovieEntity>

    // ✅ 查詢最愛電影（假設 title 是唯一的最愛條件，可依需求調整）
    @Query("SELECT * FROM movies WHERE title IN (:favoriteTitles)")
    suspend fun getFavoriteMovies(favoriteTitles: List<String>): List<MovieEntity>

}
