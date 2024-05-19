package com.kravchenko.netflux.repository

import com.kravchenko.netflux.models.Movie
import com.kravchenko.netflux.network.Api
import com.kravchenko.netflux.utils.PathLanguage
import com.kravchenko.netflux.utils.PathRegion

class MovieRepository(private val service: Api) {

    suspend fun getNowPlayingMovies(
        apiKey: String,
        language: String? = PathLanguage.ENGLISH.code,
        page: Int? = 1,
        region: String? = PathRegion.US.code
    ): List<Movie> {
        return service.getNowPlayingMovies(language, page, region, apiKey).results
    }

    suspend fun getPopularMovies(
        apiKey: String,
        language: String? = PathLanguage.ENGLISH.code,
        page: Int? = 1,
        region: String? = PathRegion.US.code
    ): List<Movie> {
        return service.getPopularMovies(language, page, region, apiKey).results
    }

    suspend fun getTopRatedMovies(
        apiKey: String,
        language: String? = PathLanguage.ENGLISH.code,
        page: Int? = 1,
        region: String? = PathRegion.US.code
    ): List<Movie> {
        return service.getTopRatedMovies(language, page, region, apiKey).results
    }

    suspend fun getUpcomingMovies(
        apiKey: String,
        language: String? = PathLanguage.ENGLISH.code,
        page: Int? = 1,
        region: String? = PathRegion.US.code
    ): List<Movie> {
        return service.getUpcomingMovies(language, page, region, apiKey).results
    }

    suspend fun getMovieDetails(
        movieId: Long,
        apiKey: String
    ): Movie {
        val movie = service.getMovieDetails(movieId, apiKey)
        val castResponse = service.getMovieCredits(movieId, apiKey)
        val videos = service.getMovieVideos(movieId, apiKey)
        return movie.copy(cast = castResponse.cast, crew = castResponse.crew, videos = videos.results)
    }
}