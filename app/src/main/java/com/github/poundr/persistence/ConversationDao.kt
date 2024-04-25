package com.github.poundr.persistence

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.github.poundr.persistence.model.ConversationEntity
import com.github.poundr.persistence.model.ConversationParticipantCrossRef
import com.github.poundr.persistence.model.ConversationParticipantEntity
import com.github.poundr.persistence.model.ConversationWithParticipants
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: ConversationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversationParticipant(conversationParticipant: ConversationParticipantEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversationParticipantCrossRef(conversationParticipantCrossRef: ConversationParticipantCrossRef)

    @Transaction
    @Query("SELECT * FROM ConversationEntity WHERE id = :conversationId")
    fun getConversationWithParticipants(conversationId: String): Flow<ConversationWithParticipants>

    @Transaction
    @Query("SELECT * FROM ConversationEntity")
    fun getConversationsWithParticipantsPagingSource(): PagingSource<Int, ConversationWithParticipants>
}