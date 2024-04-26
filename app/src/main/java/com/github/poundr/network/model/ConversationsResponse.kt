package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ConversationsResponse(
    @Json(name = "entries") val entries: List<ConversationResponse>,
    @Json(name = "nextPage") val nextPage: Int?
)