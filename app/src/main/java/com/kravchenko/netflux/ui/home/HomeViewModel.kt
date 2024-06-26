package com.kravchenko.netflux.ui.home

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.kravchenko.netflux.BuildConfig
import com.kravchenko.netflux.models.Movie
import com.kravchenko.netflux.repository.MovieRepository
import com.kravchenko.netflux.ui.landing.LandingState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val imageLoader: ImageLoader,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _homeState = MutableStateFlow<HomeState>(HomeState.Loading)
    val homeState: StateFlow<HomeState> = _homeState

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            try {
                println(BuildConfig.TMDB_API_KEY)
                val nowPlayingMovies = repository.getNowPlayingMovies()
                val popularMovies = repository.getPopularMovies()
                val topRatedMovies = repository.getTopRatedMovies()
                val upcomingMovies = repository.getUpcomingMovies()

                val backdropPaths = popularMovies.map { it.backdropPath }.shuffled()
                val loadedPosters = mutableListOf<Drawable>()
                backdropPaths.forEach { backdropPath ->
                    val request = ImageRequest.Builder(context)
                        .data("https://image.tmdb.org/t/p/w1280$backdropPath")
                        .build()
                    val result = imageLoader.execute(request)
                    if (result.drawable != null) {
                        loadedPosters.add(result.drawable!!)
                    }
                }

                _homeState.value = HomeState.Success(
                    nowPlayingMovies = nowPlayingMovies,
                    popularMovies = popularMovies,
                    topRatedMovies = topRatedMovies,
                    upcomingMovies = upcomingMovies,
                    posters = loadedPosters
                )
            } catch (e: Exception) {
                _homeState.value = HomeState.Error("Failed to load movie details.")
            }
        }
    }
}