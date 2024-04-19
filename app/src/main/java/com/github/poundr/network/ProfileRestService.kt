package com.github.poundr.network

import com.github.poundr.model.FullProfileList
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileRestService {
//    @GET("v4/me/muted-profiles")
//    suspend fun getMeMutedProfiles(): IndividualChatMuteRequest
//
//    @POST("v3.1/flags/{id}")
//    suspend fun postFlags(@Path("id") id: String, @Body reportProfileV31Request: ReportProfileV31Request): Unit
//
//    @GET("v3.1/flags/{id}")
//    suspend fun getFlags(@Path("id") id: String): ReportProfileV31Response
//
//    @POST("/v4/profiles/status")
//    suspend fun postProfilesStatus(@Body profileStatusRequest: ProfileStatusRequest): ProfileStatusResponse
//
//    @DELETE("v3/me/profile")
//    suspend fun deleteMeProfile(): Unit
//
//    @GET("/v3/places/search")
//    suspend fun getPlacesSearch(@Query("placeName") placeName: String): ExploreSearchResultList
//
//    @HTTP(hasBody = true, method = "DELETE", path = "/v3/me/profile/images")
//    suspend fun deleteMeProfileImages(@Body deleteApprovedProfilePhotoRequest: DeleteApprovedProfilePhotoRequest): Unit
//
//    @POST("v4/views")
//    suspend fun postViews(@Body profileViewsRequest: ProfileViewsRequest): Unit
//
//    @PATCH("/v4/me/profile")
//    suspend fun patchMeProfile(@Body updateProfileV4Request: UpdateProfileV4Request): Unit
//
//    @POST("/v4/profiles/reachable")
//    suspend fun postProfilesReachable(@Body reachableProfiles: ReachableProfiles): ReachableProfiles

    @GET("v4/me/profile")
    suspend fun getMeProfile(): FullProfileList

//    @GET("v4/profiles/{id}")
//    suspend fun getProfiles(@Path("id") id: String): FullProfileList
//
//    @DELETE("v3/me/favorites/{id}")
//    suspend fun deleteMeFavorites(@Path("id") id: String): ResponseBody
//
//    @POST("v4/views/{profileId}")
//    suspend fun postViews(@Path("profileId") profileId: String): Unit
//
//    @PUT("v3.1/me/profile")
//    suspend fun putMeProfile(@Body updateProfileRequest: UpdateProfileRequest): Unit
//
//    @POST("v3/profiles")
//    suspend fun postProfiles(@Body profilesRequest: ProfilesRequest): ProfileList
//
//    @POST("v3/me/favorites/{id}")
//    suspend fun postMeFavorites(@Path("id") id: String): ResponseBody
}