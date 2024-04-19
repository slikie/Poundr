package com.github.poundr.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginEmailRequest(
    @Json(name = "email") val email: String?,
    @Json(name = "password") val password: String?,
    @Json(name = "authToken") val authToken: String?,
    @Json(name = "token") val token: String?
)
