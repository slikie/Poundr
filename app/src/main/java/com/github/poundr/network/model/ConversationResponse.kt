package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ConversationResponse(
    @Json(name = "conversationId") val conversationId: String,
    @Json(name = "name") val name: String?,
    @Json(name = "participants") val participants: List<ConversationParticipantResponse>,
    @Json(name = "preview") val preview: ConversationPreviewResponse?,
    @Json(name = "unreadCount") val unreadCount: Int?,
    @Json(name = "lastActivityTimestamp") val lastActivityTimestamp: Long?,
    @Json(name = "muted") val muted: Boolean?,
    @Json(name = "pinned") val pinned: Boolean?,
    @Json(name = "favorite") val favorite: Boolean?,
    @Json(name = "rightNow") val rightNow: RightNowStatus?,
    @Json(name = "onlineUntil") val onlineUntil: Long?,
    @Json(name = "translatable") val translatable: Boolean?
)