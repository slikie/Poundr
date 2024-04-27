package com.github.poundr.ui.model

data class CascadeItem(
    val profileId: String,
    val name: String?,
    val avatarId: String?,
    val lastOnline: Long,
    val favorite: Boolean,
    val unreadCount: Int,
)
