package com.github.poundr.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ConversationPreviewResponse(
    @Json(name = "albumContentId") val albumContentId: String?,
    @Json(name = "albumContentReply") val albumContentReply: String?,
    @Json(name = "albumId") val albumId: String?,
    @Json(name = "chat1MessageId") val chat1MessageId: String?,
    @Json(name = "chat1Type") val chat1Type: String?,
    @Json(name = "duration") val duration: Long?,
    @Json(name = "imageHash") val imageHash: String?,
    @Json(name = "messageId") val messageId: String?,
    @Json(name = "lat") val lat: Double?,
    @Json(name = "lon") val lon: Double?,
    @Json(name = "photoContentReply") val photoContentReply: String?,
    @Json(name = "senderId") val senderId: Long?,
    @Json(name = "text") val text: String?,
    @Json(name = "type") val type: String?,
    @Json(name = "url") val url: String?
)