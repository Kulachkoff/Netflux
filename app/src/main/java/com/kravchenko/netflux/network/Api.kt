package com.kravchenko.netflux.network

import com.kravchenko.netflux.models.Cast
import com.kravchenko.netflux.models.Movie
import com.kravchenko.netflux.models.MovieVideo
import com.kravchenko.netflux.utils.PathConstant
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("${PathConstant.BASE_MOVIE_URL}/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?,
        @Header("Authorization") apiKey: String
    ): MovieResponse

    @GET("${PathConstant.BASE_MOVIE_URL}/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?,
        @Header("Authorization") apiKey: String
    ): MovieResponse

    @GET("${PathConstant.BASE_MOVIE_URL}/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?,
        @Header("Authorization") apiKey: String
    ): MovieResponse

    @GET("${PathConstant.BASE_MOVIE_URL}/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?,
        @Header("Authorization") apiKey: String
    ): MovieResponse

    @GET("${PathConstant.BASE_MOVIE_URL}/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Header("Authorization") apiKey: String
    ): Movie

    @GET("${PathConstant.BASE_MOVIE_URL}/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Long,
        @Header("Authorization") apiKey: String
    ): CreditsResponse

    @GET("${PathConstant.BASE_MOVIE_URL}/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Long,
        @Header("Authorization") apiKey: String
    ): VideoResponse
}

data class MovieResponse(
    val results: List<Movie>
)

data class CreditsResponse(
    val cast: List<Cast>,
    val crew: List<Cast>
)

data class VideoResponse(
    val results: List<MovieVideo>
)