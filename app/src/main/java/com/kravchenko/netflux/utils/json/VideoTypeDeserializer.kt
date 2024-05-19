package com.kravchenko.netflux.utils.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.kravchenko.netflux.utils.VideoType
import java.lang.reflect.Type

class VideoTypeDeserializer : JsonDeserializer<VideoType> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): VideoType {
        return when(json?.asJsonPrimitive?.asString) {
            "Trailer" -> VideoType.TRAILER
            "Teaser" -> VideoType.TEASER
            "Featurette" -> VideoType.FEATURETTE
            else -> VideoType.UNKNOWN
        }
    }

}