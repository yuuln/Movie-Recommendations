package com.example.movierecommendations.viewmodel


import androidx.lifecycle.*
import com.example.movierecommendations.repository.MovieRepository

import com.example.movierecommendations.model.MovieEntity
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _allMovies = MutableLiveData<List<MovieEntity>>()
    val allMovies: LiveData<List<MovieEntity>> = _allMovies

    fun loadFavoriteMovies(favoriteTitles: List<String>) {
        viewModelScope.launch {
            _allMovies.value = repository.getFavoriteMovies(favoriteTitles)
        }
    }

    fun loadAllMovies() {
        viewModelScope.launch {
            val movies = repository.getAllMovies()
            _allMovies.value = movies
        }
    }
    fun loadMoviesByCategory(category: String) {
        viewModelScope.launch {
            _allMovies.value = repository.getMoviesByCategory(category)
        }
    }
}