package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Identity {
    @Json(name = "gender") val gender: Gender? = null
    @Json(name = "pronouns") val pronouns: Pronouns? = null

    @JsonClass(generateAdapter = true)
    class Gender {
        @Json(name = "genderCategory") val genderCategory: Int? = null
        @Json(name = "genderDisplay") val genderDisplay: String? = null
    }

    @JsonClass(generateAdapter = true)
    class Pronouns {
        @Json(name = "pronounsCategory") val pronounsCategory: Int? = null
        @Json(name = "pronounsDisplay") val pronounsDisplay: String? = null
    }
}