package com.kravchenko.netflux.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kravchenko.netflux.utils.json.GenderDeserializer
import com.kravchenko.netflux.utils.json.VideoTypeDeserializer

object GsonFactory {
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(VideoType::class.java, VideoTypeDeserializer())
        .registerTypeAdapter(Gender::class.java, GenderDeserializer())
        .serializeNulls()
        .create()
}