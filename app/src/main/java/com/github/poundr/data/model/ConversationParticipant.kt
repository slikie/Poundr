package com.github.poundr.data.model

data class ConversationParticipant(
    val id: Long,
    val name: String?,
    val primaryPhotoId: String?,
    val distance: Float?,
    val lastSeen: Long?,
)
