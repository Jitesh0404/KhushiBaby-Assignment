package com.jitesh.assignment_khushibaby.repository

import com.jitesh.assignment_khushibaby.APIS.MovieApiService
import com.jitesh.assignment_khushibaby.data.Movie
import com.jitesh.assignment_khushibaby.data.MovieResponse
import javax.inject.Inject

// Endpoints functions
class MovieRepository @Inject constructor(
    private val apiService: MovieApiService
) {
    suspend fun getPopularMovies(page: Int): MovieResponse {
        return apiService.getPopularMovies(page = page)
    }

    suspend fun searchMovies(query: String, page: Int): MovieResponse {
        return apiService.searchMovies(query = query, page = page)
    }

    suspend fun getMovieDetails(movieId: Int): Movie {
        return apiService.getMovieDetails(movieId = movieId)
    }
}