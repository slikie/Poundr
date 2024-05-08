package com.github.poundr.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.github.poundr.network.model.MessageResponse
import com.github.poundr.ui.component.JumpToBottom
import com.github.poundr.ui.component.UserInput
import com.github.poundr.vm.ConversationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    viewModel: ConversationViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val myUserId = viewModel.myUserId
    val messages = viewModel.messages.flow.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Icon")
                            Column {
                                Text("Name")
                                Text("Distance")
                            }
                        }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = { /*TODO*/ }
            )
        },
        // Exclude ime and navigation bar padding so this can be added by the UserInput composable
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets
            .exclude(WindowInsets.navigationBars),
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = scrollState,
                    reverseLayout = true,
                ) {
                    //for (index in messages.indices) {
                    //                val prevAuthor = messages.getOrNull(index - 1)?.author
                    //                val nextAuthor = messages.getOrNull(index + 1)?.author
                    //                val content = messages[index]
                    //                val isFirstMessageByAuthor = prevAuthor != content.author
                    //                val isLastMessageByAuthor = nextAuthor != content.author
                    //
                    //                // Hardcode day dividers for simplicity
                    //                if (index == messages.size - 1) {
                    //                    item {
                    //                        DayHeader("20 Aug")
                    //                    }
                    //                } else if (index == 2) {
                    //                    item {
                    //                        DayHeader("Today")
                    //                    }
                    //                }
                    //
                    //                item {
                    //                    Message(
                    //                        onAuthorClick = { name -> navigateToProfile(name) },
                    //                        msg = content,
                    //                        isUserMe = content.author == authorMe,
                    //                        isFirstMessageByAuthor = isFirstMessageByAuthor,
                    //                        isLastMessageByAuthor = isLastMessageByAuthor
                    //                    )
                    //                }
                    //            }
                    items(
                        count = messages.itemCount,
                        key = messages.itemKey { it.id },
                        contentType = { messages[it]?.type }
                    ) {
                        val prevMessage = if (it == 0) null else messages[it - 1]
                        val message = messages[it]
                        val nextMessage = if (it == messages.itemCount - 1) null else messages[it + 1]
                        if (message != null) {
                            val isFirstMessageByAuthor = prevMessage?.senderId != message.senderId
                            val isLastMessageByAuthor = nextMessage?.senderId != message.senderId
                            val isUserMe = message.senderId == myUserId

                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val modifier = Modifier
                                    .align(
                                        if (isUserMe) Alignment.CenterEnd else Alignment.CenterStart
                                    )
                                when (message.type) {
                                    MessageResponse.TEXT -> {
                                        Text(text = message.textText!!, modifier = modifier)
                                    }

                                    else -> {
                                        Text(
                                            text = "Unsupported message (type: ${message.type})",
                                            modifier = modifier
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Jump to bottom button shows up when user scrolls past a threshold.
                // Convert to pixels:
                val jumpThreshold = with(LocalDensity.current) {
                    56.dp.toPx()
                }

                // Show the button if the first visible item is not the first one or if the offset is
                // greater than the threshold.
                val jumpToBottomButtonEnabled by remember {
                    derivedStateOf {
                        scrollState.firstVisibleItemIndex != 0 ||
                                scrollState.firstVisibleItemScrollOffset > jumpThreshold
                    }
                }

                JumpToBottom(
                    // Only show if the scroller is not at the bottom
                    enabled = jumpToBottomButtonEnabled,
                    onClicked = {
                        scope.launch {
                            scrollState.animateScrollToItem(0)
                        }
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }

            UserInput(
                onMessageSent = { content ->
//                    uiState.addMessage(
//                        Message(authorMe, content, timeNow)
//                    )
                },
                resetScroll = {
                    scope.launch {
                        scrollState.scrollToItem(0)
                    }
                },
                // let this element handle the padding so that the elevation is shown behind the
                // navigation bar
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
            )
        }
    }
}