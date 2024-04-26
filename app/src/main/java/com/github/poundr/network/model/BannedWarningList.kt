package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BannedWarningList(
    @Json(name = "warnings") val warnings: List<BannedWarning?>?
)
