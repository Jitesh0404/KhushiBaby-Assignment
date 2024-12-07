package com.jitesh.assignment_khushibaby.Screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jitesh.assignment_khushibaby.ViewModel.MovieViewModel
import com.jitesh.assignment_khushibaby.data.Movie

@Composable
fun MovieListScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    // Create a derived state to detect when we're near the end of the list
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != null &&
                    lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 5 &&
                    !uiState.isLoading &&
                    uiState.canLoadMore
        }
    }

    // Load more items when we're near the end of the list
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            if (searchQuery.isEmpty()) {
                viewModel.loadNextPage()
            } else {
                viewModel.searchMoviesNextPage(searchQuery)
            }
        }
    }

    Column {
        SearchBar(
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
                if (it.isNotEmpty()) {
                    viewModel.searchMovies(it)
                } else {
                    viewModel.loadMovies()
                }
            }

        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(
                items = uiState.movies,
                key = { movie -> movie.id }  // Add key for better performance
            ) { movie ->
                MovieItem(
                    movie = movie,
                    onClick = { onMovieClick(movie) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Show loading indicator at the bottom while loading more items
            if (uiState.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            // Show end of content message when there's no more data to load
            if (!uiState.canLoadMore && uiState.movies.isNotEmpty()) {
                item {
                    Text(
                        text = "No more movies to load",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        if (uiState.error != null) {
            ErrorMessage(message = uiState.error!!)
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        singleLine = true,
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Search movies...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
    )
}

@Composable
fun MovieItem(
    movie: Movie,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185${movie.poster_path}",
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = movie.release_date ?: "Soon",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}