package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ProfileItemData(
    @Json(name = "profileId") val profileId: Long,
    @Json(name = "lastOnline") val lastOnline: Long?,
    @Json(name = "isFavorite") val isFavorite: Boolean?,
    @Json(name = "isBoosting") val isBoosting: Boolean?,
    @Json(name = "isTeleporting") val isTeleporting: Boolean?,
    @Json(name = "lastViewed") val lastViewed: Long?,
    @Json(name = "hasChattedInLast24Hrs") val hasChattedInLast24Hrs: Boolean?,
    @Json(name = "lastMessageTimestamp") val lastMessageTimestamp: Long?,
    @Json(name = "photoMediaHashes") val photoMediaHashes: List<String>,
    @Json(name = "displayName") val displayName: String?,
    @Json(name = "age") val age: Int?,
    @Json(name = "approximateDistance") val approximateDistance: Boolean?,
    @Json(name = "distanceMeters") val distanceMeters: Float?,
    @Json(name = "aboutMe") val aboutMe: String?,
    @Json(name = "ethnicity") val ethnicity: Int?,
    @Json(name = "lookingFor") val lookingFor: List<Int>?,
    @Json(name = "relationshipStatus") val relationshipStatus: Int?,
    @Json(name = "tribes") val tribes: List<Int>?,
    @Json(name = "bodyType") val bodyType: Int?,
    @Json(name = "sexualPosition") val sexualPosition: Int?,
    @Json(name = "hivStatus") val hivStatus: Int?,
    @Json(name = "lastTestedDate") val lastTestedDate: Long?,
    @Json(name = "heightCm") val heightCm: Double?,
    @Json(name = "weightGrams") val weightGrams: Double?,
    @Json(name = "socialNetworks") val socialNetworks: List<SocialNetwork2>?,
    @Json(name = "acceptsNsfwPics") val acceptsNSFWPics: Int?,
    @Json(name = "meetAt") val meetAt: List<Int>?,
    @Json(name = "tags") val tags: List<String>?,
    @Json(name = "genders") val genders: List<Int>?,
    @Json(name = "pronouns") val pronouns: List<Int>?,
    @Json(name = "vaccines") val vaccines: List<Int>?,
    @Json(name = "rightNow") val rightNow: RightNowStatus?,
    @Json(name = "rightNowText") val rightNowText: String?,
    @Json(name = "rightNowPosted") val rightNowPosted: Long?,
    @Json(name = "unreadCount") val unreadCount: Int?,
    @Json(name = "arrivalDays") val arrivalDays: Int?
)