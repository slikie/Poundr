package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SocialNetwork {
    @Json(name = "userId") val userId: String? = null
}