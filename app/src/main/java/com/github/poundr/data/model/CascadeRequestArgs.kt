package com.github.poundr.data.model

data class CascadeRequestArgs(
    val nearbyGeoHash: String? = null,
    val exploreGeoHash: String? = null,
    val onlineOnly: Boolean? = null,
    val photoOnly: Boolean? = null,
    val faceOnly: Boolean? = null,
    val notRecentlyChatted: Boolean? = null,
    val hasAlbum: Boolean? = null,
    val ageMin: Int? = null,
    val ageMax: Int? = null,
    val heightCmMin: Float? = null,
    val heightCmMax: Float? = null,
    val weightGramsMin: Float? = null,
    val weightGramsMax: Float? = null,
    val tribes: String? = null,
    val lookingFor: String? = null,
    val relationshipStatuses: String? = null,
    val bodyTypes: String? = null,
    val sexualPositions: String? = null,
    val meetAt: String? = null,
    val nsfwPics: String? = null,
    val tags: String? = null,
    val fresh: Boolean? = null,
    val genders: String? = null,
    val rightNow: Boolean? = null,
    val favorites: Boolean? = null
)