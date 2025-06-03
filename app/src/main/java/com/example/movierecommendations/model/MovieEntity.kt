package com.example.movierecommendations.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val title: String,
    val category: String,
    val description: String,
    val imageUrl: String,
    val isFavorite: Boolean = false
) : Serializable