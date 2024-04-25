package com.github.poundr.data

import android.util.Log
import com.github.poundr.data.mapper.ConversationMapper
import com.github.poundr.data.model.ConversationInfo
import com.github.poundr.network.ConversationService
import com.github.poundr.persistence.ConversationDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "ConversationRepository"

@Singleton
class ConversationRepository @Inject constructor(
    private val conversationDao: ConversationDao,
    private val conversationService: ConversationService
) {

    //    @Json(name = "unreadOnly") val unreadOnly: Boolean?,
    //    @Json(name = "favoritesOnly") val favoritesOnly: Boolean?,
    //    @Json(name = "onlineNowOnly") val onlineNowOnly: Boolean?
    suspend fun getConversations(page: Int, unreadOnly: Boolean, favoritesOnly: Boolean, onlineNowOnly: Boolean) {

    }

    suspend fun getConversationInfo(conversationId: String): Flow<ConversationInfo> {
        return conversationDao.getConversationWithParticipants(conversationId)
            .onStart { fetchConversationInfo(conversationId) }
            .distinctUntilChanged()
            .map(ConversationMapper::mapToConversationInfo)
    }

    private suspend fun fetchConversationInfo(conversationId: String) {
        try {
            val response = conversationService.getConversations(listOf(conversationId))[0]
            Log.d(TAG, "fetchConversationInfo: $response")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}