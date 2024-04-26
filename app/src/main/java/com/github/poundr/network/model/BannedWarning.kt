package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BannedWarning(
    @Json(name = "warning") val warning: String?,
    @Json(name = "count") val count: Int?
)