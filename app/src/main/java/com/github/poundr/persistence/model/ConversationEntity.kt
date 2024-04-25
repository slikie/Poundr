package com.github.poundr.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConversationEntity(
    @PrimaryKey val id: String,
    val name: String?
)