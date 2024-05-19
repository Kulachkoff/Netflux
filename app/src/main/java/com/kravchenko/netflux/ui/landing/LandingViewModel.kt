package com.kravchenko.netflux.ui.landing

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.kravchenko.netflux.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val imageLoader: ImageLoader,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _landingState = MutableStateFlow<LandingState>(LandingState.Loading)
    val landingState: StateFlow<LandingState> = _landingState

    init {
        fetchPopularMovies()
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            _landingState.value = LandingState.Loading
            try {
                val movies = repository.getPopularMovies("Bearer tmdb_api_key")
                val posterPaths = movies.map { it.posterPath }.shuffled()

                val loadedPosters = mutableListOf<Drawable>()
                posterPaths.forEach { posterPath ->
                    val request = ImageRequest.Builder(context)
                        .data("https://image.tmdb.org/t/p/w1280$posterPath")
                        .build()
                    val result = imageLoader.execute(request)
                    if (result.drawable != null) {
                        loadedPosters.add(result.drawable!!)
                    }
                }

                _landingState.value = LandingState.Success(loadedPosters)
            } catch (e: Exception) {
                // TODO: Handle the exception
                _landingState.value = LandingState.Error("Failed to load movie details.")
            }
        }
    }
}
