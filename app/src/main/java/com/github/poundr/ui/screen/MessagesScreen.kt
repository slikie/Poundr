package com.github.poundr.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.github.poundr.ui.component.MessageRow
import com.github.poundr.ui.component.PullRefreshBox
import com.github.poundr.vm.MessagesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    viewModel: MessagesViewModel = hiltViewModel(),
    onConversationClick: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Messages")
                },
            )
        }
    ) { innerPadding ->
        val messages = viewModel.messages.flow.collectAsLazyPagingItems()
        val isRefreshing = messages.loadState.refresh == LoadState.Loading

        PullRefreshBox(
            refreshing = isRefreshing,
            onRefresh = { messages.refresh() },
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(
                    count = messages.itemCount,
                    key = messages.itemKey { it.conversation.id }
                ) { index ->
                    val message = messages[index]!!
                    MessageRow(
                        imageId = message.participants.firstOrNull()?.primaryPhotoId,
                        name = message.conversation.name ?: "",
                        lastMessage = "",
                        lastMessageTime = 0,
                        onClick = { onConversationClick(message.conversation.id) }
                    )
                }
            }
        }
    }
}