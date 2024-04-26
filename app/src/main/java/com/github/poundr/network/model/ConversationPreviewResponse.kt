package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ConversationPreviewResponse(
    @Json(name = "albumContentId") val albumContentId: Long?, // AlbumContentReply (AlbumContentReaction?)
    @Json(name = "albumContentReply") val albumContentReply: String?,
    @Json(name = "albumId") val albumId: Long?, // AlbumContentReply (AlbumContentReaction?)
    @Json(name = "chat1MessageId") val chat1MessageId: String?, // Legacy, don't use
    @Json(name = "chat1Type") val chat1Type: String?, // Legacy, don't use
    @Json(name = "duration") val duration: Long?, // Audio (Video? | NonExpiringVideo?)
    @Json(name = "imageHash") val imageHash: String?, // ProfilePhotoReply
    @Json(name = "messageId") val messageId: String?, // Always
    @Json(name = "lat") val lat: Double?, // Location
    @Json(name = "lon") val lon: Double?, // Location
    @Json(name = "photoContentReply") val photoContentReply: String?, // ProfilePhotoReply
    @Json(name = "senderId") val senderId: Long?,
    @Json(name = "text") val text: String?, // Text
    @Json(name = "type") val type: String?, // Always
    @Json(name = "url") val url: String? // Gaymoji | Giphy | Image
)