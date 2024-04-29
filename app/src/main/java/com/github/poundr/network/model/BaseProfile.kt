package com.github.poundr.network.model

import com.squareup.moshi.Json

abstract class BaseProfile(
    @Json(name = "aboutMe") var aboutMe: String? = null,
    @Json(name = "nsfw") var acceptNSFWPics: Boolean? = null,
    @Json(name = "age") var age: Int? = null,
    @Json(name = "approximateDistance") var approximateDistance: Boolean? = null,
    @Json(name = "arrival_days") var arrivalDays: Int? = null,
    @Json(name = "bodyType") var bodyType: Int? = null,
    @Json(name = "displayName") var displayName: String? = null,
    @Json(name = "distance") var distance: Double? = null,
    @Json(name = "ethnicity") var ethnicity: Int? = null,
    @Json(name = "genders") var genders: List<Int>? = null,
    @Json(name = "grindrTribes") var grindrTribes: List<Int>? = null,
    @Json(name = "hashtags") var hashtags: List<String>? = null,
    @Json(name = "height") var height: Double? = null,
    @Json(name = "hivStatus") var hivStatus: Int? = null,
    @Json(name = "identity") var identity: Identity? = null,
    @Json(name = "lastTestedDate") var lastTestedDate: Long? = null,
    @Json(name = "lookingFor") var lookingFor: List<Int>? = null,
    @Json(name = "meetAt") var meetAt: List<Int>? = null,
    @Json(name = "profileImageMediaHash") var profileImageMediaHash: String? = null,
    @Json(name = "profileTags") var profileTags: List<String>? = null,
    @Json(name = "pronouns") var pronouns: List<Int>? = null,
    @Json(name = "relationshipStatus") var relationshipStatus: Int? = null,
    @Json(name = "sexualPosition") var sexualPosition: Int? = null,
    @Json(name = "showAge") var showAge: Boolean? = null,
    @Json(name = "showDistance") var showDistance: Boolean? = null,
    @Json(name = "socialNetworks") var socialNetworks: SocialNetworks? = null,
    @Json(name = "vaccines") var vaccines: List<Int>? = null,
    @Json(name = "weight") var weight: Double? = null
)
