package com.github.poundr.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UpdateLocationRequest(
    @Json(name = "geohash") val geohash: String
)