package com.github.poundr.ui.screen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.github.poundr.ui.component.MessageRow
import com.github.poundr.ui.component.PullRefreshBox
import com.github.poundr.vm.MessagesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationsScreen(
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
                windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
            )
        },
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { innerPadding ->
        val listState = rememberLazyListState()
        val messages = viewModel.messages.flow.collectAsLazyPagingItems()
        val isRefreshing = messages.loadState.refresh == LoadState.Loading

        LaunchedEffect(messages) {
            snapshotFlow { messages.itemCount }
                .collect { itemCount ->
                    if (itemCount > 0 && listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0) {
                        listState.scrollToItem(0)
                    }
                }
        }

        PullRefreshBox(
            refreshing = isRefreshing,
            onRefresh = { messages.refresh() },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                items(
                    count = messages.itemCount,
                    key = messages.itemKey { it.id }
                ) { index ->
                    val message = messages[index]!!
                    MessageRow(
                        modifier = Modifier.animateItem(),
                        imageId = message.profilePicHash,
                        name = message.name,
                        lastMessage = message.preview?.text,
                        lastMessageTime = message.lastActivityTimestamp,
                        onClick = { onConversationClick(message.id) }
                    )
                }
            }
        }
    }
}