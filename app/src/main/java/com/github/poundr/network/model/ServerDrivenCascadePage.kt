package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ServerDrivenCascadePage(
    @Json(name = "items") val items: List<ServerDrivenCascadeApiItem>,
    @Json(name = "nextPage") val nextPage: Int?
)