package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class BaseProfile {
    @Json(name = "aboutMe") val aboutMe: String? = null
    @Json(name = "nsfw") val acceptNSFWPics: Boolean? = null
    @Json(name = "age") val age: Int? = null
    @Json(name = "approximateDistance") val approximateDistance: Boolean? = null
    @Json(name = "arrival_days") val arrivalDays: Int? = null
    @Json(name = "bodyType") val bodyType: Int? = null
    @Json(name = "displayName") val displayName: String? = null
    @Json(name = "distance") val distance: Double? = null
    @Json(name = "ethnicity") val ethnicity: Int? = null
    @Json(name = "genders") val genders: List<Int?>? = null
    @Json(name = "grindrTribes") val grindrTribes: List<Int?>? = null
    @Json(name = "hashtags") val hashtags: List<String?>? = null
    @Json(name = "height") val height: Double? = null
    @Json(name = "hivStatus") val hivStatus: Int? = null
    @Json(name = "identity") val identity: Identity? = null
    @Json(name = "lastTestedDate") val lastTestedDate: Long? = null
    @Json(name = "lookingFor") val lookingFor: List<Int?>? = null
    @Json(name = "meetAt") val meetAt: List<Int?>? = null
    @Json(name = "profileImageMediaHash") val profileImageMediaHash: String? = null
    @Json(name = "profileTags") val profileTags: List<String?>? = null
    @Json(name = "pronouns") val pronouns: List<Int?>? = null
    @Json(name = "relationshipStatus") val relationshipStatus: Int? = null
    @Json(name = "sexualPosition") val sexualPosition: Int? = null
    @Json(name = "showAge") val showAge: Boolean? = null
    @Json(name = "showDistance") val showDistance: Boolean? = null
    @Json(name = "socialNetworks") val socialNetworks: SocialNetworks? = null
    @Json(name = "vaccines") val vaccines: List<Int?>? = null
    @Json(name = "weight") val weight: Double? = null
}
