package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageReactionResponse(
    @Json(name = "profileId") val profileId: Long?,
    @Json(name = "reactionType") val reactionType: Int?
)
