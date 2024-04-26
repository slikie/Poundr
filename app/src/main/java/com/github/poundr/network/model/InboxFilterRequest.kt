package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class InboxFilterRequest(
    @Json(name = "unreadOnly") val unreadOnly: Boolean?,
    @Json(name = "favoritesOnly") val favoritesOnly: Boolean?,
    @Json(name = "onlineNowOnly") val onlineNowOnly: Boolean?
)