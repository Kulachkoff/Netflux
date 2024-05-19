package com.kravchenko.netflux.ui.movie

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kravchenko.netflux.models.Movie
import com.kravchenko.netflux.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieDetailsState = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Loading)
    val movieDetailsState: StateFlow<MovieDetailsState> = _movieDetailsState

    fun fetchMovieDetails(movieId: Long) {
        viewModelScope.launch {
            _movieDetailsState.value = MovieDetailsState.Loading
            try {
                val movie = repository.getMovieDetails(movieId)
                _movieDetailsState.value = MovieDetailsState.Success(movie)

                Log.d("MovieDetailsViewModel", "Movie: $movie")
                Log.d("MovieDetailsViewModel", "Cast: ${movie.cast}")
                Log.d("MovieDetailsViewModel", "Crew: ${movie.crew}")
                Log.d("MovieDetailsViewModel", "Actors: ${movie.cast.filter { it.department == "Acting" }}")
                Log.d("MovieDetailsViewModel", "Director: ${movie.crew.find { it.job == "Director" }}")
                Log.d("MovieDetailsViewModel", "Videos: ${movie.videos}")
            } catch (e: Exception) {
                _movieDetailsState.value = MovieDetailsState.Error("Failed to load movie details.")
            }
        }
    }
}