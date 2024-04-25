package com.github.poundr.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.poundr.model.InboxFilterRequest
import com.github.poundr.network.ConversationService
import com.github.poundr.persistence.PoundrDatabase
import com.github.poundr.persistence.model.ConversationEntity
import com.github.poundr.persistence.model.ConversationParticipantCrossRef
import com.github.poundr.persistence.model.ConversationParticipantEntity
import com.github.poundr.persistence.model.ConversationWithParticipants
import javax.inject.Inject

private const val TAG = "ConversationRM"
private const val FIRST_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class ConversationRemoteMediator @Inject constructor(
    private val inboxFilterRequest: InboxFilterRequest,
    private val poundrDatabase: PoundrDatabase,
    private val conversationService: ConversationService
) : RemoteMediator<Int, ConversationWithParticipants>() {
    private val conversationDao = poundrDatabase.conversationDao()

    private var nextPage: Int? = FIRST_PAGE

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ConversationWithParticipants>
    ): MediatorResult {
        return try {
            val pageToLoad = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE
                LoadType.PREPEND -> null
                LoadType.APPEND -> nextPage
            } ?: return MediatorResult.Success(endOfPaginationReached = true)

            val response = conversationService.getInbox(pageToLoad, inboxFilterRequest)

            poundrDatabase.withTransaction {
                response.entries.forEach { conversation ->
                    val conversationEntity = ConversationEntity(
                        id = conversation.conversationId,
                        name = conversation.name
                    )
                    conversationDao.insertConversation(conversationEntity)

                    conversation.participants.forEach { participant ->
                        val participantEntity = ConversationParticipantEntity(
                            id = participant.profileId,
                            name = conversation.name,
                            primaryPhotoId = participant.primaryMediaHash,
                            distance = null, // FIXME: Add distance to api response
                            lastSeen = participant.lastOnline
                        )
                        conversationDao.insertConversationParticipant(participantEntity)

                        val crossRef = ConversationParticipantCrossRef(
                            conversationId = conversationEntity.id,
                            participantId = participantEntity.id
                        )
                        conversationDao.insertConversationParticipantCrossRef(crossRef)
                    }
                }
            }

            nextPage = response.nextPage

            Log.d(TAG, "Loaded page: $pageToLoad with ${response.entries.size} conversations")

            MediatorResult.Success(endOfPaginationReached = response.entries.isEmpty())
        } catch (e: Exception) {
            Log.e(TAG, "load: Error loading conversations", e)
            MediatorResult.Error(e)
        }
    }
}