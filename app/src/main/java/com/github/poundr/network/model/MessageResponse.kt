package com.github.poundr.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed class MessageResponse(
    @Json(name = "messageId") var messageId: String = "",
    @Json(name = "conversationId") var conversationId: String = "",
    @Json(name = "senderId") var senderId: Long = 0,
    @Json(name = "timestamp") var timestamp: Long = 0,
    @Json(name = "type") var type: String = "",
    @Json(name = "chat1Type") var chat1Type: String? = null,
    @Json(name = "unsent") var unsent: Boolean? = null,
    @Json(name = "reactions") var reactions: List<MessageReactionResponse> = emptyList(),
    @Json(name = "replyToMessage") var replyToMessage: MessageResponse? = null,
    @Json(name = "dynamic") var dynamic: Boolean? = null,
    @Json(name = "refValue") var refValue: String? = null,
) {
    sealed class BaseAlbum(
        @Json(name = "body") var body: Body? = null
    ) : MessageResponse() {
        @JsonClass(generateAdapter = true)
        class Body(
            @Json(name = "albumId") var albumId: Long? = null,
            @Json(name = "albumContentId") var albumContentId: Long? = null,
            @Json(name = "albumContentReply") var albumContentReply: String? = null,
            @Json(name = "isUnshared") var isUnshared: Boolean? = null,
            @Json(name = "ownerProfileId") var ownerProfileId: Long? = null,
            @Json(name = "coverUrl") var coverUrl: String? = null,
            @Json(name = "previewUrl") var previewUrl: String? = null,
            @Json(name = "hasPhoto") var hasPhoto: Boolean? = null,
            @Json(name = "hasVideo") var hasVideo: Boolean? = null,
            @Json(name = "isViewable") var isViewable: Boolean? = null,
            @Json(name = "albumNumber") var albumNumber: Int? = null,
            @Json(name = "totalAlbumsShared") var totalAlbumsShared: Int? = null,
            @Json(name = "hasUnseenContent") var hasUnseenContent: Boolean? = null
        )
    }

    @JsonClass(generateAdapter = true)
    class Album : BaseAlbum()

    @JsonClass(generateAdapter = true)
    class AlbumContentReaction : BaseAlbum()

    @JsonClass(generateAdapter = true)
    class AlbumContentReply : BaseAlbum()

    @JsonClass(generateAdapter = true)
    class Audio(
        @Json(name = "body") var body: Body? = null
    ) : MessageResponse() {
        @JsonClass(generateAdapter = true)
        class Body(
            @Json(name = "mediaId") var mediaId: Long? = null,
            @Json(name = "duration") var duration: Long? = null,
            @Json(name = "length") var length: Long? = null,
            @Json(name = "imageHash") var imageHash: String? = null,
            @Json(name = "mediaHash") var mediaHash: String? = null,
            @Json(name = "mimeType") var mimeType: String? = null,
            @Json(name = "url") var url: String? = null
        )
    }

    @JsonClass(generateAdapter = true)
    class ExpiringImage(
        @Json(name = "body") var body: Body
    ) : MessageResponse() {
        @JsonClass(generateAdapter = true)
        class Body(
            @Json(name = "duration") var duration: Long,
            @Json(name = "height") var height: Int,
            @Json(name = "expiresAt") var expiresAt: Long,
            @Json(name = "mediaId") var mediaId: Long,
            @Json(name = "url") var url: String,
            @Json(name = "width") var width: Int,
            @Json(name = "viewsRemaining") var viewsRemaining: Int
        )
    }

    @JsonClass(generateAdapter = true)
    class Video(
        @Json(name = "body") var body: Body? = null
    ) : MessageResponse() {
        @JsonClass(generateAdapter = true)
        class Body(
            @Json(name = "mediaId") var mediaId: Long? = null,
            @Json(name = "url") var url: String? = null,
            @Json(name = "contentType") var contentType: String? = null,
            @Json(name = "length") var length: Long? = null,
            @Json(name = "maxViews") var maxViews: Int? = null,
            @Json(name = "viewsRemaining") var viewsRemaining: Int? = null,
            @Json(name = "fileCacheKey") var fileCacheKey: String? = null,
            @Json(name = "looping") var looping: Boolean? = null,
        )
    }

    @JsonClass(generateAdapter = true)
    class Gaymoji(
        @Json(name = "body") var body: Body
    ) : MessageResponse() {
        @JsonClass(generateAdapter = true)
        class Body(
            @Json(name = "imageHash") var imageHash: String
        )
    }

    @JsonClass(generateAdapter = true)
    class Giphy(
        @Json(name = "body") var body: Body
    ) : MessageResponse() {
        @JsonClass(generateAdapter = true)
        class Body(
            @Json(name = "height") var height: Int,
            @Json(name = "id") var id: String,
            @Json(name = "width") var width: Int,
            @Json(name = "previewPath") var previewPath: String,
            @Json(name = "stillPath") var stillPath: String,
            @Json(name = "urlPath") var urlPath: String,
            @Json(name = "imageHash") var imageHash: String
        )
    }

    @JsonClass(generateAdapter = true)
    class Image(
        @Json(name = "body") var body: Body
    ) : MessageResponse() {
        @JsonClass(generateAdapter = true)
        class Body(
            @Json(name = "expiresAt") var expiresAt: Long,
            @Json(name = "mediaId") var mediaId: Long,
            @Json(name = "url") var url: String,
            @Json(name = "width") var width: Int,
            @Json(name = "height") var height: Int,
            @Json(name = "imageHash") var imageHash: String,
            @Json(name = "imageType") var imageType: Int? = null,
            @Json(name = "mimeType") var mimeType: String? = null
        )
    }

    @JsonClass(generateAdapter = true)
    class Location(
        @Json(name = "body") var body: Body? = null
    ) : MessageResponse() {
        @JsonClass(generateAdapter = true)
        class Body(
            @Json(name = "lat") var lat: Double? = null,
            @Json(name = "lon") var lon: Double? = null
        )
    }

    @JsonClass(generateAdapter = true)
    class ProfilePhotoReply(
        @Json(name = "body") var body: Body? = null
    ) : MessageResponse() {
        @JsonClass(generateAdapter = true)
        class Body(
            @Json(name = "imageHash") var imageHash: String? = null,
            @Json(name = "photoContentReply") var photoContentReply: String? = null
        )
    }

    @JsonClass(generateAdapter = true)
    class Retract : MessageResponse()

    @JsonClass(generateAdapter = true)
    class Text(
        @Json(name = "body") var body: Body
    ) : MessageResponse() {
        @JsonClass(generateAdapter = true)
        class Body(
            @Json(name = "text") var text: String
        )
    }

    @JsonClass(generateAdapter = true)
    class Unknown : MessageResponse()

    @JsonClass(generateAdapter = true)
    class NonExpiringVideo(
        @Json(name = "body") var body: Body? = null
    ) : MessageResponse() {
        @JsonClass(generateAdapter = true)
        class Body(
            @Json(name = "mediaId") var mediaId: Long? = null,
            @Json(name = "url") var url: String? = null,
            @Json(name = "contentType") var contentType: String? = null,
            @Json(name = "length") var length: Long? = null,
            @Json(name = "maxViews") var maxViews: Int? = null,
            @Json(name = "viewCount") var viewCount: Int? = null,
            @Json(name = "looping") var looping: Boolean? = null,
            @Json(name = "fileCacheKey") var fileCacheKey: String? = null
        )
    }
    
    companion object {
        const val KEY = "type"
        const val ALBUM = "Album"
        const val ALBUM_CONTENT_REACTION = "AlbumContentReaction"
        const val ALBUM_CONTENT_REPLY = "AlbumContentReply"
        const val AUDIO = "Audio"
        const val EXPIRING_IMAGE = "ExpiringImage"
        const val VIDEO = "Video"
        const val GAYMOJI = "Gaymoji"
        const val GIPHY = "Giphy"
        const val IMAGE = "Image"
        const val LOCATION = "Location"
        const val PRIVATE_VIDEO = "PrivateVideo"
        const val PROFILE_PHOTO_REPLY = "ProfilePhotoReply"
        const val RETRACT = "Retract"
        const val TEXT = "Text"
        const val UNKNOWN = "Unknown"
        const val NON_EXPIRING_VIDEO = "NonExpiringVideo"
    }
}