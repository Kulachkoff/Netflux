package com.kravchenko.netflux.utils.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.kravchenko.netflux.utils.Gender
import java.lang.reflect.Type

class GenderDeserializer : JsonDeserializer<Gender> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Gender {
        return when(json?.asJsonPrimitive?.asInt) {
            1 -> Gender.FEMALE
            2 -> Gender.MALE
            else -> Gender.OTHER
        }
    }

}