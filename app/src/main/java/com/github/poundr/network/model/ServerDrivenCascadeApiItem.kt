package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

interface ServerDrivenCascadeApiItem {

    // FavsHeaderV1
    @JsonClass(generateAdapter = true)
    class FavoritesHeader(
        @Json(name = "data") val data: FavoritesHeaderData
    ) : ServerDrivenCascadeApiItem

    // FavoritesHeaderNoFreeResultsV1
    @JsonClass(generateAdapter = true)
    class FavoritesNoFreeResults : ServerDrivenCascadeApiItem

    // FavoritesHeaderNoXtraResultsV1
    @JsonClass(generateAdapter = true)
    class FavoritesNoXtraResults : ServerDrivenCascadeApiItem

    // Insertable
//    @JsonClass(generateAdapter = true)
//    class Insertable(
//        @Json(name = "data") val data: InsertableData
//    ) : ServerDrivenCascadeApiItem()

    // PartialProfileV1
    @JsonClass(generateAdapter = true)
    class PartialProfile(
        @Json(name = "data") val data: PartialProfileItemData
    ) : ServerDrivenCascadeApiItem

    // FullProfileV1
    @JsonClass(generateAdapter = true)
    class Profile(
        @Json(name = "data") val data: ProfileItemData
    ) : ServerDrivenCascadeApiItem

    // Unknown
    object Unknown : ServerDrivenCascadeApiItem

    companion object {
        const val KEY = "type"
        const val FAVS_HEADER = "favs_header_v1"
        const val FAVS_NO_FREE_RESULTS = "favorites_header_no_free_results_v1"
        const val FAVS_NO_XTRA_RESULTS = "favorites_header_no_xtra_results_v1"
        const val FULL_PROFILE = "full_profile_v1"
        const val INSERTABLE = ""
        const val PARTIAL_PROFILE = "partial_profile_v1"
    }

}