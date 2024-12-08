package com.jitesh.assignment_khushibaby.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jitesh.assignment_khushibaby.data.Movie
import com.jitesh.assignment_khushibaby.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovieUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val canLoadMore: Boolean = true
)

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState

    private var currentSearchQuery: String? = null

    init {
        loadMovies()
    }

    fun loadMovies() {
        currentSearchQuery = null
        _uiState.update { it.copy(
            movies = emptyList(),
            currentPage = 1,
            canLoadMore = true
        ) }
        loadNextPage()
    }

    fun loadNextPage() {
        if (_uiState.value.isLoading || !_uiState.value.canLoadMore) return

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                val response = movieRepository.getPopularMovies(_uiState.value.currentPage)

                _uiState.update { currentState ->
                    currentState.copy(
                        movies = currentState.movies + response.results,
                        currentPage = currentState.currentPage + 1,
                        isLoading = false,
                        error = null,
                        canLoadMore = response.page < response.total_pages
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = e.message,
                    canLoadMore = false
                ) }
            }
        }
    }

//     Search Movie functionality Implementation
    fun searchMovies(query: String) {
        Log.d("Query is ",query)
        currentSearchQuery = query
        _uiState.update { it.copy(
            movies = emptyList(),
            currentPage = 1,
            canLoadMore = true
        ) }
        searchMoviesNextPage(query)
    }

//    Pagination to search movie on next gap
    fun searchMoviesNextPage(query: String) {
        if (_uiState.value.isLoading || !_uiState.value.canLoadMore) return

        Log.d("msg ========== ",query)
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                val response = movieRepository.searchMovies(
                    query = query,
                    page = _uiState.value.currentPage
                )
                Log.d("Response of viewModel is ",response.toString())
                _uiState.update { currentState ->
                    currentState.copy(
                        movies = currentState.movies + response.results,
                        currentPage = currentState.currentPage + 1,
                        isLoading = false,
                        error = null,
                        canLoadMore = response.page < response.total_pages
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = e.message,
                    canLoadMore = false
                ) }
            }
        }
    }
}