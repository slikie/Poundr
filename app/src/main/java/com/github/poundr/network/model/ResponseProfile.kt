package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ResponseProfile : BaseProfile() {
    @Json(name = "badgeCount") val badgeCount: Int? = null
    @Json(name = "created") val created: Long? = null
    @Json(name = "foundVia") val foundVia: String? = null
    @Json(name = "lastChatTimestamp") val lastChatTimestamp: Long? = null
    @Json(name = "lastUpdatedTime") val lastUpdatedTime: Long? = null
    @Json(name = "lastViewed") val lastViewed: Long? = null
    @Json(name = "medias") val photos: List<ProfilePhoto>? = null
    @Json(name = "profileId") val profileId: String? = null
    @Json(name = "rightNowPosted") val rightNowPostCreatedTime: Long? = null
    @Json(name = "rightNow") val rightNowStatus: RightNowStatus? = null
    @Json(name = "rightNowText") val rightNowText: String? = null
    @Json(name = "seen") val seen: Long? = null
    @Json(name = "isFavorite") val isFavorite: Boolean? = null
    @Json(name = "isNew") val isNew: Boolean? = null
    @Json(name = "isTeleporting") val isTeleporting: Boolean? = null
}
