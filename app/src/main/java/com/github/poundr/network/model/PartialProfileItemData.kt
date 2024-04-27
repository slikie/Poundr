package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PartialProfileItemData(
    @Json(name = "profileId") val profileId: Long,
    @Json(name = "lastOnline") val lastOnline: Long?,
    @Json(name = "approximateDistance") val approximateDistance: Boolean?,
    @Json(name = "distanceMeters") val distanceMeters: Float?,
    @Json(name = "isFavorite") val isFavorite: Boolean?,
    @Json(name = "isTeleporting") val isTeleporting: Boolean?,
    @Json(name = "isBoosting") val isBoosting: Boolean?,
    @Json(name = "rightNow") val rightNow: RightNowStatus?,
    @Json(name = "displayName") val displayName: String?,
    @Json(name = "photoMediaHashes") val photoMediaHashes: List<String>,
    @Json(name = "lastViewed") val lastViewed: Long?,
    @Json(name = "hasChattedInLast24Hrs") val hasChattedInLast24Hrs: Boolean?,
    @Json(name = "lastMessageTimestamp") val lastMessageTimestamp: Long?,
    @Json(name = "upsellItemType") val upsellItemType: String?,
    @Json(name = "unreadCount") val unreadCount: Int?,
    @Json(name = "arrivalDays") val arrivalDays: Int?
)