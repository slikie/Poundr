package com.github.poundr.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["participantId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ConversationEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(index = true) val participantId: Long,
    var muted: Boolean = false,
    var pinned: Boolean = false,
    var lastActivityTimestamp: Long,
    var unreadCount: Int = 0,
)