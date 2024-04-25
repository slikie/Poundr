package com.github.poundr.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ConversationParticipantResponse(
    @Json(name = "profileId") val profileId: Long,
    @Json(name = "primaryMediaHash") val primaryMediaHash: String?,
    @Json(name = "lastOnline") val lastOnline: Long?
)