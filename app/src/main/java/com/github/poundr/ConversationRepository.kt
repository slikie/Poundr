package com.github.poundr

import com.github.poundr.network.model.MessageResponse
import com.github.poundr.persistence.PoundrDatabase
import com.github.poundr.persistence.model.ConversationEntity
import com.github.poundr.persistence.model.ConversationMessageEntity
import com.github.poundr.persistence.model.UserEntity

class ConversationRepository(
    private val poundrDatabase: PoundrDatabase,
) {
    private val userDao = poundrDatabase.userDao()
    private val conversationDao = poundrDatabase.conversationDao()

    suspend fun putMessage(message: MessageResponse, senderName: String?, senderProfileImageMediaHash: String?) {
        val user = UserEntity(
            id = message.senderId,
            name = senderName,
            profilePicMediaHash = senderProfileImageMediaHash
        )
        userDao.upsertUserFromFirebaseMessageResponse(user)

        val conversation = ConversationEntity(
            id = message.conversationId,
            participantId = message.senderId,
            lastActivityTimestamp = message.timestamp
        )
        conversationDao.upsertConversationFromMessageResponse(conversation)

        putMessage(message)
    }

    suspend fun putMessage(message: MessageResponse) {
        val messageEntity = when (message) {
//                        is MessageResponse.Album -> {
//                            Log.w(TAG, "Album not implemented")
//                        }
//                        is MessageResponse.AlbumContentReaction -> {
//                            Log.w(TAG, "AlbumContentReaction not implemented")
//                        }
//                        is MessageResponse.AlbumContentReply -> {
//                            Log.w(TAG, "AlbumContentReply not implemented")
//                        }
//                        is MessageResponse.Audio -> {
//                            Log.w(TAG, "Audio not implemented")
//                        }
            is MessageResponse.ExpiringImage -> ConversationMessageEntity(
                id = message.messageId,
                conversationId = message.conversationId,
                senderId = message.senderId,
                timestamp = message.timestamp,
                unsent = message.unsent ?: false,
                type = message.type,
                expiringImageDuration = message.body.duration,
                expiringImageExpiresAt = message.body.expiresAt,
                expiringImageHeight = message.body.height,
                expiringImageMediaId = message.body.mediaId,
                expiringImageUrl = message.body.url,
                expiringImageViewsRemaining = message.body.viewsRemaining,
                expiringImageWidth = message.body.width
            )
//                        is MessageResponse.Video -> {
//                            Log.w(TAG, "Video not implemented")
//                        }
            is MessageResponse.Gaymoji -> ConversationMessageEntity(
                id = message.messageId,
                conversationId = message.conversationId,
                senderId = message.senderId,
                timestamp = message.timestamp,
                unsent = message.unsent ?: false,
                type = message.type,
                gaymojiImageHash = message.body.imageHash
            )
            is MessageResponse.Giphy -> ConversationMessageEntity(
                id = message.messageId,
                conversationId = message.conversationId,
                senderId = message.senderId,
                timestamp = message.timestamp,
                unsent = message.unsent ?: false,
                type = message.type,
                giphyHeight = message.body.height,
                giphyId = message.body.id,
                giphyImageHash = message.body.imageHash,
                giphyPreviewPath = message.body.previewPath,
                giphyStillPath = message.body.stillPath,
                giphyUrlPath = message.body.urlPath,
                giphyWidth = message.body.width
            )
            is MessageResponse.Image -> ConversationMessageEntity(
                id = message.messageId,
                conversationId = message.conversationId,
                senderId = message.senderId,
                timestamp = message.timestamp,
                unsent = message.unsent ?: false,
                type = message.type,
                imageExpiresAt = message.body.expiresAt,
                imageHeight = message.body.height,
                imageImageHash = message.body.imageHash,
                imageMediaId = message.body.mediaId,
                imageUrl = message.body.url,
                imageWidth = message.body.width
            )
//                        is MessageResponse.Location -> {
//                            Log.w(TAG, "Location not implemented")
//                        }
//                        is MessageResponse.ProfilePhotoReply -> {
//                            Log.w(TAG, "ProfilePhotoReply not implemented")
//                        }
//                        is MessageResponse.Retract -> {
//                            Log.w(TAG, "Retract not implemented")
//                        }
            is MessageResponse.Text -> ConversationMessageEntity(
                id = message.messageId,
                conversationId = message.conversationId,
                senderId = message.senderId,
                timestamp = message.timestamp,
                unsent = message.unsent ?: false,
                type = message.type,
                textText = message.body.text
            )
//                        is MessageResponse.Unknown -> {
//                            Log.d(TAG, "Unknown message type: ${message.type}")
//                        }
//                        is MessageResponse.NonExpiringVideo -> {
//                            Log.w(TAG, "NonExpiringVideo not implemented")
//                        }
            else -> null
        }

        if (messageEntity != null) {
            conversationDao.upsertConversationMessage(messageEntity)
        }
    }
}