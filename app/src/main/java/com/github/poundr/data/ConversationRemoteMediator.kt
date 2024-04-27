package com.github.poundr.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.poundr.data.model.ConversationRequestArgs
import com.github.poundr.network.ConversationService
import com.github.poundr.persistence.PoundrDatabase
import com.github.poundr.persistence.model.ConversationRowEntity

private const val TAG = "ConversationRM"

@OptIn(ExperimentalPagingApi::class)
class ConversationRemoteMediator(
    private val conversationRequestArgs: ConversationRequestArgs,
    private val poundrDatabase: PoundrDatabase,
    private val conversationService: ConversationService
) : RemoteMediator<String, ConversationRowEntity>() {
    private val userDao = poundrDatabase.userDao()
    private val conversationDao = poundrDatabase.conversationDao()

    private var nextPage: String? = null

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<String, ConversationRowEntity>
    ): MediatorResult {
        return try {
            val pageToLoad = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> nextPage
            }

            val response = conversationService.getMessages(
                conversationId = conversationRequestArgs.conversationId,
                pageKey = pageToLoad
            )

            poundrDatabase.withTransaction {
                response.messages.forEach { conversation ->

                }
            }

            nextPage = response.messages.sortedBy { it.timestamp }.lastOrNull()?.messageId

            MediatorResult.Success(endOfPaginationReached = response.messages.isEmpty())
        } catch (e: Exception) {
            Log.e(TAG, "load: Error loading messages", e)
            MediatorResult.Error(e)
        }
    }
}