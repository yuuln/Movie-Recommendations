package com.example.movierecommendations.repository

import com.example.movierecommendations.model.MovieEntity
import com.example.movierecommendations.model.MovieDao

class MovieRepository(private val movieDao: MovieDao) {

    // 取得所有電影
    suspend fun getAllMovies(): List<MovieEntity> {
        return movieDao.getAll()
    }

    // 插入多部電影
    suspend fun insertMovies(movies: List<MovieEntity>) {
        movieDao.insertAll(movies)
    }

    // 插入一部電影
    suspend fun insertMovie(movie: MovieEntity) {
        movieDao.insert(movie)
    }

    // 根據分類查詢
    suspend fun getMoviesByCategory(category: String): List<MovieEntity> {
        return movieDao.getMoviesByCategory(category)
    }

    // 依喜好查詢（可擴充）
    suspend fun getFavoriteMovies(favoriteTitles: List<String>): List<MovieEntity> {
        return movieDao.getFavoriteMovies(favoriteTitles)
    }

}

