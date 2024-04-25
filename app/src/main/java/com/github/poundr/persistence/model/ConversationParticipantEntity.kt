package com.github.poundr.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConversationParticipantEntity(
    @PrimaryKey val id: Long,
    val name: String?,
    val primaryPhotoId: String?,
    val distance: Float?,
    val lastSeen: Long?,
)
