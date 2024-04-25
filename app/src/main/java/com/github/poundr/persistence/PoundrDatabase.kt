package com.github.poundr.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.poundr.persistence.model.ConversationEntity
import com.github.poundr.persistence.model.ConversationParticipantCrossRef
import com.github.poundr.persistence.model.ConversationParticipantEntity

@Database(
    entities = [
        ConversationEntity::class,
        ConversationParticipantEntity::class,
        ConversationParticipantCrossRef::class,
    ],
    version = 1
)
abstract class PoundrDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
}