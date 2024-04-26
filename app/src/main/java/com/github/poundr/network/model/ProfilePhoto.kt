package com.github.poundr.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ProfilePhoto {
    val mediaHash: String? = null
    val state: Int? = null
    val reason: String? = null
    val order: Int? = null
    val profileId: String? = null
    val isSelected: Boolean? = null
    val width: Int? = null
    val height: Int? = null
}