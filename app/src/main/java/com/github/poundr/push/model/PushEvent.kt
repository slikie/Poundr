package com.github.poundr.push.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PushEvent(
    @Json(name = "type") val type: String,
    @Json(name = "title") val title: String,
    @Json(name = "body") val body: String,
    @Json(name = "silent") val silent: Boolean,
    @Json(name = "data") val data: PushEventData
)