package com.github.poundr.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlbumEntity(
    @PrimaryKey val albumId: Long,
    val albumContentId: Long?,
    val albumContentReply: String?,
    val isUnshared: Boolean?,
    val ownerProfileId: Long?,
    val coverUrl: String?,
    val previewUrl: String?,
    val hasPhoto: Boolean?,
    val hasVideo: Boolean?,
    val isViewable: Boolean?,
    val albumNumber: Int?,
    val totalAlbumsShared: Int?,
    val hasUnseenContent: Boolean?
)
