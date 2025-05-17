package com.example.movierecommendations.model
import java.io.Serializable

data class Movie(
    val title: String,
    val category: String,
    val description: String,
    val imageResId: Int
):Serializable //轉換成一連串的位元資方便放進 Bundle 或 Intent 在 Android Activity 間傳遞
//test