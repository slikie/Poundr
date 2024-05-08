package com.github.poundr.ui.model

data class CascadeItem(
    val profileId: Long,
    val name: String?,
    val avatarId: String?,
    val lastOnline: Long,
    val favorite: Boolean,
    val unreadCount: Int,
    val position: Int
)
