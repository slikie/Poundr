package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FavoritesHeaderData(
    @Json(name = "displayed") val displayed: Int?,
    @Json(name = "available") val available: Int?,
    @Json(name = "total") val total: Int?
)