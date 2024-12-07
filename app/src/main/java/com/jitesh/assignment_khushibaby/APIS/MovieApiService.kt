package com.jitesh.assignment_khushibaby.APIS

import com.jitesh.assignment_khushibaby.data.Movie
import com.jitesh.assignment_khushibaby.data.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

var API_KEYS = "1083b158a9a0be8bf803a4a9f6a9336e";
var BASE_URL = "https://api.themoviedb.org/3/"
interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEYS
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEYS
    ): Movie

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEYS
    ): MovieResponse
}