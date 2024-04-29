package com.github.poundr.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.poundr.data.model.ConversationMessagesRequestArgs
import com.github.poundr.network.ConversationService
import com.github.poundr.network.model.MessageResponse
import com.github.poundr.persistence.PoundrDatabase
import com.github.poundr.persistence.model.ConversationMessageEntity

private const val TAG = "ConversationRM"

@OptIn(ExperimentalPagingApi::class)
class ConversationMessagesRemoteMediator(
    private val conversationMessagesRequestArgs: ConversationMessagesRequestArgs,
    private val poundrDatabase: PoundrDatabase,
    private val conversationService: ConversationService
) : RemoteMediator<Int, ConversationMessageEntity>() {
    private val userDao = poundrDatabase.userDao()
    private val conversationDao = poundrDatabase.conversationDao()

    private var nextPage: String? = null

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ConversationMessageEntity>
    ): MediatorResult {
        return try {
            val pageToLoad = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> nextPage
            }

            val response = conversationService.getMessages(
                conversationId = conversationMessagesRequestArgs.conversationId,
                pageKey = pageToLoad
            )

            poundrDatabase.withTransaction {
                response.messages.forEach { message ->
                    ConversationMessageEntity(
                        id = message.messageId,
                        conversationId = message.conversationId,
                        senderId = message.senderId,
                        timestamp = message.timestamp,
                        unsent = message.unsent ?: false,
                        type = message.type
                    )
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
                        conversationDao.insertConversationMessage(messageEntity)
                    }
                }
            }

            nextPage = response.messages.lastOrNull()?.messageId

            MediatorResult.Success(endOfPaginationReached = response.messages.isEmpty())
        } catch (e: Exception) {
            Log.e(TAG, "load: Error loading messages", e)
            MediatorResult.Error(e)
        }
    }
}