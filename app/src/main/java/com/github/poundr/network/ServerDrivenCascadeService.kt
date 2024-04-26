package com.github.poundr.network

import com.github.poundr.network.model.ServerDrivenCascadePage
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerDrivenCascadeService {
    @GET("v1/cascade")
    suspend fun getCascadePage(
        @Query("nearbyGeoHash") nearbyGeoHash: String?,
        @Query("exploreGeoHash") exploreGeoHash: String?,
        @Query("onlineOnly") onlineOnly: Boolean?,
        @Query("photoOnly") photoOnly: Boolean?,
        @Query("faceOnly") faceOnly: Boolean?,
        @Query("notRecentlyChatted") notRecentlyChatted: Boolean?,
        @Query("hasAlbum") hasAlbum: Boolean?,
        @Query("ageMin") ageMin: Int?,
        @Query("ageMax") ageMax: Int?,
        @Query("heightCmMin") heightCmMin: Float?,
        @Query("heightCmMax") heightCmMax: Float?,
        @Query("weightGramsMin") weightGramsMin: Float?,
        @Query("weightGramsMax") weightGramsMax: Float?,
        @Query("tribes") tribes: String?,
        @Query("lookingFor") lookingFor: String?,
        @Query("relationshipStatuses") relationshipStatuses: String?,
        @Query("bodyTypes") bodyTypes: String?,
        @Query("sexualPositions") sexualPositions: String?,
        @Query("meetAt") meetAt: String?,
        @Query("nsfwPics") nsfwPics: String?,
        @Query("tags") tags: String?,
        @Query("fresh") fresh: Boolean?,
        @Query("pageNumber") pageNumber: Int?,
        @Query("genders") genders: String?,
        @Query("rightNow") rightNow: Boolean?,
        @Query("favorites") favorites: Boolean?
    ): ServerDrivenCascadePage
}