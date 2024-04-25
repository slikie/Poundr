package com.github.poundr.persistence.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ConversationWithParticipants(
    @Embedded val conversation: ConversationEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ConversationParticipantCrossRef::class,
            parentColumn = "conversationId",
            entityColumn = "participantId"
        )
    )
    val participants: List<ConversationParticipantEntity>
)
