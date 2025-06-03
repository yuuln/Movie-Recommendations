package com.example.movierecommendations.model
import java.io.Serializable

data class Movie(
    val title: String,
    val category: String,
    val description: String,
    val imageResId: String
):Serializable
//test
//uu