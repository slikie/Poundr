package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FullProfileList(
    @Json(name = "profiles") val profiles: List<ResponseProfile?>?
)
