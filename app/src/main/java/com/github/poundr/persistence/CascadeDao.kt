package com.github.poundr.persistence

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.poundr.persistence.model.CascadeItemEntity
import com.github.poundr.ui.model.CascadeItem

@Dao
interface CascadeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCascadeItem(cascadeItem: CascadeItemEntity)

    @Query("DELETE FROM CascadeItemEntity")
    suspend fun deleteAllCascadeItems()

    @Query("""
        SELECT
            user.id AS profileId,
            user.name AS name,
            user.profilePicMediaHash AS avatarId,
            user.lastSeen AS lastOnline,
            user.favorite AS favorite,
            conversation.unreadCount AS unreadCount,
            item.position AS position
        FROM 
            CascadeItemEntity AS item
            JOIN UserEntity AS user ON item.profileId = user.id
            LEFT JOIN ConversationEntity AS conversation ON item.profileId = conversation.participantId
        ORDER BY item.position ASC
    """)
    fun getCascadeItemsPagingSource(): PagingSource<Int, CascadeItem>
}