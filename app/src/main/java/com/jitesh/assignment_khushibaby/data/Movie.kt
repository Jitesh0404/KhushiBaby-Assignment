package com.jitesh.assignment_khushibaby.data

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int,
    val popularity: Double,
    val original_language: String,
    val original_title: String,
    val adult: Boolean,
    val backdrop_path: String?,
    val video: Boolean,
    val genre_ids: List<Int>
)