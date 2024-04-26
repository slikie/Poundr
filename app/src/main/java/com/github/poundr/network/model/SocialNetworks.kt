package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SocialNetworks {
    @Json(name = "twitter") val twitter: SocialNetwork? = null
    @Json(name = "facebook") val facebook: SocialNetwork? = null
    @Json(name = "instagram") val instagram: SocialNetwork? = null
}