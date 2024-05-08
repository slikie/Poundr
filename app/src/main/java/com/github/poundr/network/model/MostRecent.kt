package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MostRecent(
    @Json(name = "photoHash") val photoHash: String?,
    @Json(name = "profileId") val profileId: String?,
    @Json(name = "timestamp") val timestamp: Long
)
