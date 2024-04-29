package com.github.poundr.persistence.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["conversationMessageId", "albumId"],
    foreignKeys = [
        ForeignKey(
            entity = ConversationMessageEntity::class,
            parentColumns = ["id"],
            childColumns = ["conversationMessageId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AlbumEntity::class,
            parentColumns = ["id"],
            childColumns = ["albumId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ConversationMessageAlbumCrossRef(
    val conversationMessageId: String,
    val albumId: Long
)