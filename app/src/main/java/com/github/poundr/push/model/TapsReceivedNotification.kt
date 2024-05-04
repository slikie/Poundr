package com.github.poundr.push.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TapsReceivedNotification(
    @Json(name = "isMutual") val isMutual: Boolean?,
    @Json(name = "recipientId") val recipientId: Long,
    @Json(name = "senderDisplayName") val senderDisplayName: String?,
    @Json(name = "senderId") val senderId: Long,
    @Json(name = "senderProfileImageHash") val senderProfileImageHash: String?,
    @Json(name = "tapType") val tapType: Int,
    @Json(name = "timestamp") val timestamp: Long
)
