package com.github.poundr.data.mapper

import com.github.poundr.data.model.ConversationInfo
import com.github.poundr.data.model.ConversationParticipant
import com.github.poundr.persistence.model.ConversationWithParticipants

object ConversationMapper {
    fun mapToConversationInfo(conversationWithParticipants: ConversationWithParticipants): ConversationInfo {
        return ConversationInfo(
            id = conversationWithParticipants.conversation.id,
            name = conversationWithParticipants.conversation.name,
            participants = conversationWithParticipants.participants.map {
                ConversationParticipant(
                    id = it.id,
                    name = it.name,
                    primaryPhotoId = it.primaryPhotoId,
                    distance = it.distance,
                    lastSeen = it.lastSeen
                )
            }
        )
    }
}