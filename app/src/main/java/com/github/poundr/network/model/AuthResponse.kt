package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    @Json(name = "profileId") val profileId: Long?,
    @Json(name = "sessionId") val sessionId: String?,
    @Json(name = "authToken") val authToken: String?,
)
