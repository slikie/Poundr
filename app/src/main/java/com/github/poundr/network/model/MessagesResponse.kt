package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessagesResponse(
    @Json(name = "messages") val messages: List<MessageResponse>,
    @Json(name = "lastReadTimestamp") val lastReadTimestamp: Long?
)
