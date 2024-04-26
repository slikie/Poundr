package com.github.poundr.network.model

import com.squareup.moshi.Json

enum class RightNowStatus {
    @Json(name = "NOT_ACTIVE") NOT_ACTIVE,
    @Json(name = "HOSTING") HOSTING,
    @Json(name = "NOT_HOSTING") NOT_HOSTING
}