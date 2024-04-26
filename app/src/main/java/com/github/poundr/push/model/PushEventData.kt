package com.github.poundr.push.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PushEventData(
    @Json(name = "profileIds") val profileIds: String?,
    @Json(name = "boostSessionId") val boostSessionId: String?,
    @Json(name = "startTime") val startTime: Long?,
    @Json(name = "endTime") val endTime: Long?,
    @Json(name = "eventType") val eventType: String?,
    @Json(name = "tapsCount") val tapsCount: Int?,
    @Json(name = "chatsCount") val chatsCount: Int?,
    @Json(name = "viewsCount") val viewsCount: Int?
)