package com.github.poundr.persistence.model

import androidx.room.Embedded

data class ConversationRowEntity(
    val id: String,
    val name: String?,
    val profilePicHash: String?,
    @Embedded(prefix = "preview.") val preview: ConversationPreviewEntity?,
    val lastActivityTimestamp: Long,
    val unreadCount: Int,
    val muted: Boolean,
    val pinned: Boolean,
    val favorite: Boolean,
)
