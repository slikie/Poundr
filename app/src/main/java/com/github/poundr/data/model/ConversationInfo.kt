package com.github.poundr.data.model

data class ConversationInfo(
    val id: String,
    val name: String?,
    val participants: List<ConversationParticipant>,
)