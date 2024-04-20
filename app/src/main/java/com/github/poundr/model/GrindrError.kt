package com.github.poundr.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GrindrError(
    @Json(name = "code") val code: Int? = null,
    @Json(name = "message") val message: String? = null,
//    @Json(name = "profileId") val profileId: String? = null,
//    @Json(name = "type") val type: String? = null,
    @Json(name = "reason") val reason: String? = null,
    @Json(name = "isBanAutomated") val isBanAutomated: Boolean? = null
) {

    companion object {
        fun unknown() = GrindrError(
            code = 5,
            message = "Unknown error"
        )
    }
}