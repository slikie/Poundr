package com.github.poundr.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.github.poundr.ConversationRepository
import com.github.poundr.data.model.ConversationMessagesRequestArgs
import com.github.poundr.network.ConversationService
import com.github.poundr.persistence.PoundrDatabase
import com.github.poundr.persistence.model.ConversationMessageEntity

private const val TAG = "ConversationRM"

@OptIn(ExperimentalPagingApi::class)
class ConversationMessagesRemoteMediator(
    private val conversationMessagesRequestArgs: ConversationMessagesRequestArgs,
    private val poundrDatabase: PoundrDatabase,
    private val conversationRepository: ConversationRepository,
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

            response.messages.forEach { message ->
                conversationRepository.putMessage(message)
            }

            nextPage = response.messages.lastOrNull()?.messageId

            MediatorResult.Success(endOfPaginationReached = response.messages.isEmpty())
        } catch (e: Exception) {
            Log.e(TAG, "load: Error loading messages", e)
            MediatorResult.Error(e)
        }
    }
}