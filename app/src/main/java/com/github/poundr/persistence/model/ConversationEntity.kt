package com.github.poundr.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConversationEntity(
    @PrimaryKey val id: String,
    val participantId: Long,
    var muted: Boolean,
    var pinned: Boolean,
    var lastActivityTimestamp: Long,
    var unreadCount: Int,
)