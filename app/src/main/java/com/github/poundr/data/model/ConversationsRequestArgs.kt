package com.github.poundr.data.model

data class ConversationsRequestArgs(
    val unreadOnly: Boolean,
    val favoritesOnly: Boolean,
    val onlineNowOnly: Boolean
)