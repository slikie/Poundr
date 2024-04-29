package com.github.poundr.persistence.model

sealed class ConversationMessage(
    val id: String,
    val senderId: Long,
    val timestamp: Long,
    val unsent: Boolean
) {
    class ExpiringImageConversationMessage(
        id: String,
        senderId: Long,
        timestamp: Long,
        unsent: Boolean,
        val expiringImageDuration: Long,
        val expiringImageExpiresAt: Long,
        val expiringImageHeight: Int,
        val expiringImageMediaId: Long,
        val expiringImageUrl: String,
        val expiringImageViewsRemaining: Int,
        val expiringImageWidth: Int
    ) : ConversationMessage(id, senderId, timestamp, unsent)

    class GaymojiConversationMessage(
        id: String,
        senderId: Long,
        timestamp: Long,
        unsent: Boolean,
        val gaymojiImageHash: String
    ) : ConversationMessage(id, senderId, timestamp, unsent)

    class GiphyConversationMessage(
        id: String,
        senderId: Long,
        timestamp: Long,
        unsent: Boolean,
        val giphyHeight: Int,
        val giphyId: String,
        val giphyImageHash: String,
        val giphyPreviewPath: String,
        val giphyStillPath: String,
        val giphyUrlPath: String,
        val giphyWidth: Int
    ) : ConversationMessage(id, senderId, timestamp, unsent)

    class ImageConversationMessage(
        id: String,
        senderId: Long,
        timestamp: Long,
        unsent: Boolean,
        val expiresAt: Long,
        val height: Int,
        val imageHash: String,
        val mediaId: Long,
        val url: String,
        val width: Int
    ) : ConversationMessage(id, senderId, timestamp, unsent)

    class TextConversationMessage(
        id: String,
        senderId: Long,
        timestamp: Long,
        unsent: Boolean,
        val text: String
    ) : ConversationMessage(id, senderId, timestamp, unsent)
}