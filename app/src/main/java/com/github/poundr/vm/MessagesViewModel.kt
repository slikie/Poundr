package com.github.poundr.vm

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.github.poundr.data.ConversationsRemoteMediator
import com.github.poundr.data.model.ConversationsRequestArgs
import com.github.poundr.network.ConversationService
import com.github.poundr.persistence.PoundrDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val poundrDatabase: PoundrDatabase,
    private val conversationService: ConversationService,
) : ViewModel() {
    @OptIn(ExperimentalPagingApi::class)
    val messages = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = ConversationsRemoteMediator(ConversationsRequestArgs(false, false, false), poundrDatabase, conversationService),
        pagingSourceFactory = { poundrDatabase.conversationDao().getConversationRowsPagingSource() }
    )
}