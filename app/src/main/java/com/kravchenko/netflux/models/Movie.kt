package com.kravchenko.netflux.models

import com.google.gson.annotations.SerializedName
import com.kravchenko.netflux.utils.MediaType
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Long,
    val title: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("belongs_to_collection")
    val collection: Collection?,
    val genres: List<Genre>,
    val video: Boolean,
    val videos: List<MovieVideo>?,
    @SerializedName("vote_average")
    val voteRating: Double,
    @SerializedName("vote_count")
    val voteCount: Long,
    val adult: Boolean,
    val popularity: Double,
    val status: String,
    @SerializedName("media_type")
    val mediaType: MediaType?,
    val cast: List<Cast>,
    val crew: List<Cast>
)