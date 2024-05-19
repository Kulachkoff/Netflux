package com.kravchenko.netflux.models

import com.google.gson.annotations.SerializedName
import com.kravchenko.netflux.utils.Gender
import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val id: Long,
    val name: String,
    val gender: Gender,
    @SerializedName("profile_path")
    val profilePath: String?,
    val character: String?,
    val order: Int?,
    val job: String?,
    @SerializedName("known_for_department")
    val department: String
)