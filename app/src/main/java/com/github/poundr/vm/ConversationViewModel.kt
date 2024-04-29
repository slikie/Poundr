package com.github.poundr.vm

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.github.poundr.data.ConversationMessagesRemoteMediator
import com.github.poundr.data.model.ConversationMessagesRequestArgs
import com.github.poundr.network.ConversationService
import com.github.poundr.persistence.PoundrDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "ConversationViewModel"

@HiltViewModel
class ConversationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    poundrDatabase: PoundrDatabase,
    conversationService: ConversationService
) : ViewModel() {
    private val conversationId: String = savedStateHandle["conversationId"]!!

    init {
        Log.d(TAG, "init: Conversation ID: $conversationId")
    }

    @OptIn(ExperimentalPagingApi::class)
    val messages = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = ConversationMessagesRemoteMediator(
            conversationMessagesRequestArgs = ConversationMessagesRequestArgs(conversationId = conversationId),
            poundrDatabase = poundrDatabase,
            conversationService = conversationService
        ),
        pagingSourceFactory = { poundrDatabase.conversationDao().getConversationMessagesPagingSource(conversationId) }
    )
    init {
        Log.d(TAG, "init: Conversation ID: $conversationId")
    }
}