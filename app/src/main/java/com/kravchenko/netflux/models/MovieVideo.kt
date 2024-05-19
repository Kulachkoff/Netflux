package com.kravchenko.netflux.models

import com.kravchenko.netflux.utils.VideoType
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideo(
    val id: String,
    val iso_639_1: String,
    val iso_3166_1: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: VideoType,
    val official: Boolean,
)