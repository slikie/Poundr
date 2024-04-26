package com.github.poundr.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.poundr.network.ConversationService
import com.github.poundr.network.model.InboxFilterRequest
import com.github.poundr.persistence.PoundrDatabase
import com.github.poundr.persistence.model.ConversationEntity
import com.github.poundr.persistence.model.ConversationPreviewEntity
import com.github.poundr.persistence.model.ConversationRowEntity
import com.github.poundr.persistence.model.UserEntity
import javax.inject.Inject

private const val TAG = "ConversationRM"
private const val FIRST_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class ConversationRemoteMediator @Inject constructor(
    private val inboxFilterRequest: InboxFilterRequest,
    private val poundrDatabase: PoundrDatabase,
    private val conversationService: ConversationService
) : RemoteMediator<Int, ConversationRowEntity>() {
    private val userDao = poundrDatabase.userDao()
    private val conversationDao = poundrDatabase.conversationDao()

    private var nextPage: Int? = FIRST_PAGE

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ConversationRowEntity>
    ): MediatorResult {
        return try {
            val pageToLoad = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE
                LoadType.PREPEND -> null
                LoadType.APPEND -> nextPage
            } ?: return MediatorResult.Success(endOfPaginationReached = true)

            val response = conversationService.getInbox(pageToLoad, inboxFilterRequest)

            Log.d(TAG, "Loaded page $pageToLoad with ${response.entries.size} conversations")

            poundrDatabase.withTransaction {
                response.entries.forEach { conversation ->
                    val participant = conversation.participants.first()
                    val conversationEntity = ConversationEntity(
                        id = conversation.conversationId,
                        participantId = participant.profileId,
                        muted = conversation.muted ?: false,
                        pinned = conversation.pinned ?: false,
                        lastActivityTimestamp = conversation.lastActivityTimestamp ?: 0,
                        unreadCount = conversation.unreadCount ?: 0,
                    )
                    conversationDao.insertConversation(conversationEntity)

                    Log.d(TAG, "Inserted conversation: $conversationEntity")

                    conversation.preview?.let {
                        val previewEntity = ConversationPreviewEntity(
                            conversationId = conversation.conversationId,
                            albumContentId = it.albumContentId,
                            albumContentReply = it.albumContentReply,
                            albumId = it.albumId,
                            duration = it.duration,
                            imageHash = it.imageHash,
                            lat = it.lat,
                            lon = it.lon,
                            photoContentReply = it.photoContentReply,
                            senderId = it.senderId,
                            text = it.text,
                            type = it.type,
                            url = it.url
                        )
                        conversationDao.insertConversationPreview(previewEntity)
                        Log.d(TAG, "Inserted preview: $previewEntity")
                    }

                    val user = UserEntity(
                        id = participant.profileId,
                        name = conversation.name,
                        distance = participant.distanceMetres,
                        profilePicMediaHash = participant.primaryMediaHash,
                        lastSeen = participant.lastOnline,
                    )
                    userDao.upsertUser(user)
                    Log.d(TAG, "Inserted user: $user")
                }
            }

            Log.d(TAG, "Inserted ${response.entries.size} conversations")

            nextPage = response.nextPage

            MediatorResult.Success(endOfPaginationReached = response.entries.isEmpty())
        } catch (e: Exception) {
            Log.e(TAG, "load: Error loading conversations", e)
            MediatorResult.Error(e)
        }
    }
}