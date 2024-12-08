package com.jitesh.assignment_khushibaby

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jitesh.assignment_khushibaby.Screens.MovieDetailScreen
import com.jitesh.assignment_khushibaby.Screens.MovieListScreen

// Navigation component to handle navigation in app
@Composable
fun MovieNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "movieList"
    ) {
        composable("movieList") {
            MovieListScreen (
                onMovieClick = { movie ->
                    navController.navigate("movieDetail/${movie.id}")
                }
            )
        }

        composable(
            route = "movieDetail/{movieId}",
            arguments = listOf(
                navArgument("movieId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            MovieDetailScreen (
                movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}