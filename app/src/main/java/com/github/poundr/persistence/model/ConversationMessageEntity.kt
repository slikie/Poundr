package com.github.poundr.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ConversationEntity::class,
            parentColumns = ["id"],
            childColumns = ["conversationId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ConversationMessageEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(index = true) val conversationId: String,
    val senderId: Long,
    val timestamp: Long,
    val unsent: Boolean,
    val type: String,

    // ExpiringImageConversationMessageBodyEntity
    val expiringImageDuration: Long? = null,
    val expiringImageExpiresAt: Long? = null,
    val expiringImageHeight: Int? = null,
    val expiringImageMediaId: Long? = null,
    val expiringImageUrl: String? = null,
    val expiringImageViewsRemaining: Int? = null,
    val expiringImageWidth: Int? = null,

    // GaymojiConversationMessageBodyEntity
    val gaymojiImageHash: String? = null,

    // GiphyConversationMessageBodyEntity
    val giphyHeight: Int? = null,
    val giphyId: String? = null,
    val giphyImageHash: String? = null,
    val giphyPreviewPath: String? = null,
    val giphyStillPath: String? = null,
    val giphyUrlPath: String? = null,
    val giphyWidth: Int? = null,

    // ImageConversationMessageBodyEntity
    val imageExpiresAt: Long? = null,
    val imageHeight: Int? = null,
    val imageImageHash: String? = null,
    val imageMediaId: Long? = null,
    val imageUrl: String? = null,
    val imageWidth: Int? = null,

    // TextConversationMessageBodyEntity
    val textText: String? = null,
)
