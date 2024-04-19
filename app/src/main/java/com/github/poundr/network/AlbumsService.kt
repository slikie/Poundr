package com.github.poundr.network

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumsService {
//    @PUT("v1/albums/red-dot")
//    suspend fun putAlbumsRedDot(): Unit
//
//    @GET("v1/albums/{albumId}/shares")
//    suspend fun getAlbumsAlbumShares(@Path("albumId") albumId: Long): AlbumsSharedWithResponse
//
//    @POST("v1/albums/{albumId}/view/content/{contentId}")
//    suspend fun postAlbumsViewContent(@Path("albumId") albumId: Long, @Path("contentId") contentId: Long): AlbumsRemainingViewsResponse
//
//    @GET("v1/albums/storage")
//    suspend fun getAlbumsStorage(): AlbumsContentLimits
//
//    @POST("v1/albums/{albumId}/content/order")
//    suspend fun postAlbumsContentOrder(@Path("albumId") albumId: Long, @Body albumsOrderRequest: AlbumsOrderRequest): Unit
//
//    @POST("/v2/albums/shares")
//    suspend fun postAlbumsShares(@Body profileAlbumsStatusRequest: ProfileAlbumsStatusRequest): ProfileAlbumStatus
//
//    @GET("v1/albums/{albumId}/content/{contentId}/processing")
//    suspend fun getAlbumsContentProcessing(@Path("albumId") albumId: Long, @Path("contentId") contentId: Long): AlbumsVideoProcessingResponse
//
//    @POST("v1/albums/{albumId}/content")
//    @Multipart
//    suspend fun postAlbumsContent(@Path("albumId") albumId: Long, @Part part: MultipartBody.Part, @Query("width") width: Int, @Query("height") height: Int): UploadAlbumContentResponse
//
//    @GET("v2/albums/{albumId}")
//    suspend fun getAlbums(@Path("albumId") albumId: Long): Album
//
//    @GET("v1/albums/red-dot")
//    suspend fun getAlbumsRedDot(): UnseenAlbumContentResponse
//
//    @POST("/v1/albums/{albumId}/content/chat/list")
//    suspend fun postAlbumsContentChatList(@Path("albumId") albumId: Long, @Body uploadAlbumContentFromChatRequest: UploadAlbumContentFromChatRequest): UploadAlbumContentFromChatResult
//
//    @GET("v1/albums/{albumId}/content/{contentId}/poster")
//    suspend fun getAlbumsContentPoster(@Path("albumId") albumId: Long, @Path("contentId") contentId: Long): AlbumsPosterResponse
//
//    @POST("v2/albums")
//    suspend fun postAlbums(@Body albumNameRequestResponse: AlbumNameRequestResponse): CreateAlbumResponse
//
//    @GET("v1/albums/{albumId}/view")
//    suspend fun getAlbumsView(@Path("albumId") albumId: Long): Unit
//
//    @POST("v1/albums/{albumId}/content/chat/list-by-id")
//    suspend fun postAlbumsContentChatListById(@Path("albumId") albumId: Long, @Body contentMediaIdList: ContentMediaIdList): Unit
//
//    @PUT("v1/albums/{albumId}/unshares")
//    suspend fun putAlbumsUnshares(@Path("albumId") albumId: Long, @Body shareOrUnshareAlbumRequest: ShareOrUnshareAlbumRequest): Unit
//
//    @GET("v1/albums")
//    suspend fun getAlbums(): AlbumsList
//
//    @GET("v2/albums/shares")
//    suspend fun getAlbumsShares(): SharedAlbumsBrief
//
//    @PUT("v1/albums/{albumId}/shares/remove")
//    suspend fun putAlbumsSharesRemove(@Path("albumId") albumId: Long): Unit
//
//    @DELETE("v1/albums/{albumId}/content/{contentId}")
//    suspend fun deleteAlbumsContent(@Path("albumId") albumId: Long, @Path("contentId") contentId: Long): Unit
//
//    @POST("v1/albums/{albumId}/shares")
//    suspend fun postAlbumsShares(@Path("albumId") albumId: Long, @Body shareOrUnshareAlbumRequest: ShareOrUnshareAlbumRequest): Unit
//
//    @DELETE("v1/albums/{albumId}")
//    suspend fun deleteAlbums(@Path("albumId") albumId: Long): Unit
//
//    @GET("v2/albums/shares/{profileId}")
//    suspend fun getAlbumsSharesProfile(@Path("profileId") profileId: Long): SharedAlbumsBrief
//
//    @PUT("v2/albums/{albumId}")
//    suspend fun putAlbums(@Path("albumId") albumId: Long, @Body albumNameRequestResponse: AlbumNameRequestResponse): AlbumNameRequestResponse
}