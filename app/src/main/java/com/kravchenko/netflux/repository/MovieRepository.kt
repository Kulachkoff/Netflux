package com.kravchenko.netflux.repository

import com.kravchenko.netflux.models.Movie
import com.kravchenko.netflux.network.Api
import com.kravchenko.netflux.utils.PathLanguage
import com.kravchenko.netflux.utils.PathRegion

class MovieRepository(private val service: Api) {

    suspend fun getNowPlayingMovies(
        language: String? = PathLanguage.ENGLISH.code,
        page: Int? = 1,
        region: String? = PathRegion.US.code
    ): List<Movie> {
        return service.getNowPlayingMovies(language, page, region).results
    }

    suspend fun getPopularMovies(
        language: String? = PathLanguage.ENGLISH.code,
        page: Int? = 1,
        region: String? = PathRegion.US.code
    ): List<Movie> {
        return service.getPopularMovies(language, page, region).results
    }

    suspend fun getTopRatedMovies(
        language: String? = PathLanguage.ENGLISH.code,
        page: Int? = 1,
        region: String? = PathRegion.US.code
    ): List<Movie> {
        return service.getTopRatedMovies(language, page, region).results
    }

    suspend fun getUpcomingMovies(
        language: String? = PathLanguage.ENGLISH.code,
        page: Int? = 1,
        region: String? = PathRegion.US.code
    ): List<Movie> {
        return service.getUpcomingMovies(language, page, region).results
    }

    suspend fun getMovieDetails(
        movieId: Long
    ): Movie {
        val movie = service.getMovieDetails(movieId)
        val castResponse = service.getMovieCredits(movieId)
        val videos = service.getMovieVideos(movieId)
        return movie.copy(cast = castResponse.cast, crew = castResponse.crew, videos = videos.results)
    }
}