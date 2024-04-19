package com.github.poundr.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    @Json(name = "profileId") val profileId: Int?,
    @Json(name = "xmppToken") val xmppToken: String?,
    @Json(name = "sessionId") val sessionId: String?,
    @Json(name = "authToken") val authToken: String?,
)
