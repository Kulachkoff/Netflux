package com.kravchenko.netflux.ui.movie

import com.kravchenko.netflux.models.Movie

sealed class MovieDetailsState {
    data object Loading : MovieDetailsState()
    data class Success(val movie: Movie) : MovieDetailsState()
    data class Error(val message: String) : MovieDetailsState()
}