package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SocialNetwork2(
    @Json(name = "site") val site: String?,
    @Json(name = "userId") val userId: String?
)