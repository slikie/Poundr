package com.github.poundr.persistence.model

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
data class ConversationPreviewEntity(
    @PrimaryKey val conversationId: String,
    val albumContentId: Long?,
    val albumContentReply: String?,
    val albumId: Long?,
    val duration: Long?,
    val imageHash: String?,
    val lat: Double?,
    val lon: Double?,
    val photoContentReply: String?,
    val senderId: Long?,
    val text: String?,
    val type: String?,
    val url: String?
)