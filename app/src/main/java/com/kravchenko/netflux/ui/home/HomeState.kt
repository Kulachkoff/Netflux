package com.kravchenko.netflux.ui.home

import android.graphics.drawable.Drawable
import com.kravchenko.netflux.models.Movie

sealed class HomeState {
    object Loading : HomeState()
    data class Success(
        val nowPlayingMovies: List<Movie>,
        val popularMovies: List<Movie>,
        val topRatedMovies: List<Movie>,
        val upcomingMovies: List<Movie>,
        val posters: List<Drawable>
    ) : HomeState()
    data class Error(val message: String) : HomeState()
}