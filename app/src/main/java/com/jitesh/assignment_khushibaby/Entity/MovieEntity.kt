package com.jitesh.assignment_khushibaby.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double,
    val adult: Boolean,
    val isFavorite: Boolean = false
)


