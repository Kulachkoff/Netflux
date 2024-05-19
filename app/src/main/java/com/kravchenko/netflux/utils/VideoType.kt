package com.kravchenko.netflux.utils

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
enum class VideoType(val type: String) {
    TRAILER("Trailer"),
    TEASER("Teaser"),
    FEATURETTE("Featurette"),
    UNKNOWN("Unknown")
}