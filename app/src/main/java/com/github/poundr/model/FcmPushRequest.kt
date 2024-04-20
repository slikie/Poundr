package com.github.poundr.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FcmPushRequest(
    @Json(name = "vendorProvidedIdentifier") val vendorProvidedIdentifier: String,
    @Json(name = "token") val token: String
)