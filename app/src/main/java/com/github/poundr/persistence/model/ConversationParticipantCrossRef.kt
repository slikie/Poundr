package com.github.poundr.persistence.model

import androidx.room.Entity

@Entity(primaryKeys = ["conversationId", "participantId"])
data class ConversationParticipantCrossRef(
    val conversationId: String,
    val participantId: Long
)
