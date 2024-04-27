package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageResponse(
    @Json(name = "messageId") val messageId: String?,
    @Json(name = "conversationId") val conversationId: String?,
    @Json(name = "senderId") val senderId: Long?,
    @Json(name = "timestamp") val timestamp: Long?,
    @Json(name = "type") val type: String?,
    @Json(name = "chat1Type") val chat1Type: String?,
    @Json(name = "body") val body: String?, // Todo: check value type
    @Json(name = "unsent") val unsent: Boolean?,
    @Json(name = "reactions") val reactions: List<MessageReactionResponse>?,
    @Json(name = "replyToMessage") val replyToMessage: MessageResponse?,
    @Json(name = "dynamic") val dynamic: Boolean?,
    @Json(name = "refValue") val refValue: String?
)
